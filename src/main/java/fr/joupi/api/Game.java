package fr.joupi.api;

import fr.joupi.api.event.GamePlayerJoinEvent;
import fr.joupi.api.event.GamePlayerLeaveEvent;
import fr.joupi.api.event.GamePlayerSpectateEvent;
import fr.joupi.api.player.GamePlayer;
import fr.joupi.api.team.GameTeam;
import fr.joupi.api.team.GameTeamColor;
import fr.joupi.api.utils.CollectionUtils;
import fr.joupi.api.waiting.WaitingRoom;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Getter
@Setter
public abstract class Game<G extends GamePlayer<T>, T extends GameTeam<G>, S extends GameSettings> implements MiniGame {

    protected final String name, id;
    protected final S settings;

    protected final ConcurrentMap<UUID, G> players;
    protected final List<T> teams;

    protected WaitingRoom waitingRoom;

    protected final GameSize gameSize;

    protected GameState state;
    protected int playerCount;

    public Game(String name, S settings) {
        this.name = name;
        this.id  = RandomStringUtils.randomAlphabetic(8);
        this.settings = settings;
        this.gameSize = settings.getGameSize();
        this.players = new ConcurrentHashMap<>(gameSize.getMaxPlayer());
        this.teams = new ArrayList<>(gameSize.getTeamNeeded());
        this.state = GameState.LOADING;
        this.playerCount = 0;
        this.preload();
    }

    public abstract G defaultGamePlayer(UUID uuid, boolean spectator);
    public abstract T defaultGameTeam(int maxSize, GameTeamColor teamColor);

    @Override
    public void preload() {
        addDefaultTeams();
    }

    @Override
    public void unload() {

    }

    @Override
    public void endGame() {
        players.values().forEach(this::leaveGame);
        //settings.disableBoard();
        unload();
    }

    @Override
    public void joinGame(Player player, boolean spectator) {
        final var uuid = player.getUniqueId();

        if (!players.containsKey(uuid)) {
            final var gamePlayer = defaultGamePlayer(uuid, spectator);
            this.playerCount += 1;

            players.put(uuid, gamePlayer);

            if (spectator)
                Bukkit.getPluginManager().callEvent(new GamePlayerSpectateEvent<>(this, gamePlayer));
            else
                Bukkit.getPluginManager().callEvent(new GamePlayerJoinEvent<>(this, gamePlayer));

            // SEEMS OKAY ?
            if (waitingRoom != null && state.is(GameState.WAIT, GameState.STARTING))
                waitingRoom.processJoin(gamePlayer);

            log.info("[{}] {} {} game.", getFullName(), player.getName(), spectator ? "spectate" : "joined");
        }
    }

    @Override
    public void leaveGame(UUID uuid) {
        ifContainsPlayer(uuid, gamePlayer -> {
            this.playerCount -= 1;
            removePlayerFromTeam(gamePlayer);

            Bukkit.getPluginManager().callEvent(new GamePlayerLeaveEvent<>(this, gamePlayer));
            players.remove(uuid);

            // NEED TO BE TESTED
            if (waitingRoom != null && state.is(GameState.WAIT, GameState.STARTING))
                waitingRoom.processLeave(gamePlayer);

            log.info("[{}] {} leave game.", getFullName(), uuid.toString());
        });
    }

    private void addDefaultTeams() {
        GameTeamColor.getColorsAsStream(gameSize.getTeamNeeded())
                .map(color -> defaultGameTeam(gameSize.getTeamMaxPlayer(), color))
                .forEach(teams::add);
    }

    public void leaveGame(GamePlayer<?> gamePlayer) {
        leaveGame(gamePlayer.getUuid());
    }

    public List<G> getAlivePlayers() {
        return players.values().stream().filter(GamePlayer::isAlive).toList();
    }

    public List<G> getSpectators() {
        return players.values().stream().filter(GamePlayer::isSpectator).toList();
    }

    public Optional<G> getPlayer(UUID uuid) {
        return Optional.ofNullable(players.get(uuid));
    }

    public Optional<G> getPlayer(Player player) {
        return getPlayer(player.getUniqueId());
    }

    public G getNullablePlayer(UUID uuid) {
        return players.get(uuid);
    }

    public G getNullablePlayer(Player player) {
        return getNullablePlayer(player.getUniqueId());
    }

    public void ifContainsPlayer(UUID uuid, Consumer<G> consumer) {
        final var gamePlayer = players.get(uuid);

        if (gamePlayer != null)
            consumer.accept(gamePlayer);
    }

    @Override
    public void broadcast(String message) {
        players.values().forEach(gamePlayer -> gamePlayer.sendMessage(message));
    }

    @Override
    public void broadcast(String... message) {
        players.values().forEach(gamePlayer -> gamePlayer.sendMessage(message));
    }

    public void broadcast(Predicate<G> filter, String message) {
        players.values().stream().filter(filter).forEach(gamePlayer -> gamePlayer.sendMessage(message));
    }

    public void broadcast(Predicate<G> filter, String... messages) {
        players.values().stream().filter(filter).forEach(gamePlayer -> gamePlayer.sendMessage(messages));
    }

    @Override
    public boolean containsPlayer(UUID uuid) {
        return players.containsKey(uuid);
    }

    @Override
    public boolean containsPlayer(Player player) {
        return players.containsKey(player.getUniqueId());
    }

    @Override
    public boolean canStart() {
        return getAlivePlayersCount() >= gameSize.getMinPlayer();
    }

    @Override
    public boolean isFull() {
        return getAlivePlayersCount() == gameSize.getMaxPlayer();
    }

    @Override
    public boolean canJoin() {
        return getAlivePlayersCount() < gameSize.getMaxPlayer();
    }

    @Override
    public int getAlivePlayersCount() {
        return getAlivePlayers().size();
    }

    @Override
    public int getSpectatorsCount() {
        return getSpectators().size();
    }

    @Override
    public int getSize() {
        return playerCount;
    }

    public void fillTeam() {
        getPlayersWithoutTeam().forEach(this::addPlayerToTeamWithLeastPlayer);
    }

    public void addPlayerToTeam(G gamePlayer, T gameTeam) {
        removePlayerFromTeam(gamePlayer);
        gameTeam.addMember(gamePlayer);
    }

    public void addPlayerToTeamWithLeastPlayer(G gamePlayer) {
        getTeamWithLeastPlayers()
                .filter(gameTeam -> gameTeam.isNotMember(gamePlayer))
                .ifPresent(gameTeam -> addPlayerToTeam(gamePlayer, gameTeam));
    }

    public void addPlayerToRandomTeam(G gamePlayer) {
        CollectionUtils.random(getReachableTeams())
                .ifPresent(team -> addPlayerToTeam(gamePlayer, team));
    }

    public void removePlayerFromTeam(G gamePlayer) {
        teams.forEach(gameTeam -> gameTeam.getMembers().removeIf(gamePlayer::equals));
    }

    public void removePlayerFromTeam(UUID uuid) {
        getPlayer(uuid).ifPresent(this::removePlayerFromTeam);
    }

    public List<G> getPlayersWithTeam() {
        return players.values().stream().filter(this::hasTeam).toList();
    }

    public List<G> getPlayersWithoutTeam() {
        return players.values().stream().filter(this::hasNoTeam).toList();
    }

    public List<T> getAliveTeams() {
        return teams.stream().filter(GameTeam::hasPlayersAlive).toList();
    }

    public List<T> getReachableTeams() {
        return teams.stream().filter(GameTeam::canJoin).toList();
    }

    public Optional<T> getTeam(GameTeamColor color) {
        return teams.stream().filter(gameTeam -> gameTeam.getColor().equals(color)).findFirst();
    }

    public Optional<T> getTeam(String teamName) {
        return teams.stream().filter(gameTeam -> gameTeam.getName().equalsIgnoreCase(teamName)).findFirst();
    }

    public Optional<T> getTeam(G gamePlayer) {
        return Optional.ofNullable(gamePlayer.getTeam());
    }

    public Optional<T> getTeam(Player player) {
        return teams.stream().filter(gameTeam -> gameTeam.isMember(player.getUniqueId())).findFirst();
    }

    public T getNullableTeam(G gamePlayer) {
        return gamePlayer.getTeam();
    }

    public Optional<T> getRandomReachableTeam() {
        return CollectionUtils.random(getReachableTeams());
    }

    public Optional<T> getTeamWithLeastPlayers() {
        return teams.stream()
                .filter(GameTeam::canJoin)
                .min(Comparator.comparingInt(GameTeam::getSize));
    }

    public Optional<T> getFirstTeamAlive() {
        return Optional.ofNullable(getAliveTeams().getFirst());
    }

    public boolean hasTeam(G gamePlayer) {
        return getTeam(gamePlayer).isPresent();
    }

    public boolean hasNoTeam(G gamePlayer) {
        return !hasTeam(gamePlayer);
    }

    public boolean oneTeamAlive() {
        return getAliveTeamsCount() == 1;
    }

    public int getAliveTeamsCount() {
        return getAliveTeams().size();
    }

    public int getReachableTeamsCount() {
        return getReachableTeams().size();
    }

    public int getTeamsCount() {
        return teams.size();
    }

    @Override
    public String getFullName() {
        return name + "-" + id;
    }

    @Override
    public void sendDebugInfoMessage(CommandSender sender) {
        sender.sendMessage("§m-----------------------------");
        sender.sendMessage("Game: " + getFullName());
        sender.sendMessage("Size: type=" + gameSize.getName() + ", min=" + gameSize.getMinPlayer() + ", max=" + gameSize.getMaxPlayer() + ", tn=" + gameSize.getTeamNeeded() + ", tm=" + gameSize.getTeamMaxPlayer());
        sender.sendMessage("Condition: cj=" + canJoin() + ", cs=" + canStart() + ", isf=" + isFull() + ", ota=" + oneTeamAlive());
        sender.sendMessage("State: " + getState());
        sender.sendMessage("Team Alive: " + getAliveTeamsCount());
        sender.sendMessage("Teams: " + getTeamsCount());
        teams.forEach(gameTeam -> sender.sendMessage(gameTeam.getName() + ": " + gameTeam.getMembers().stream().map(GamePlayer::getPlayer).map(Player::getName).collect(Collectors.joining(", "))));

        sender.sendMessage("Players: " + getSize() + " (" + getAlivePlayersCount() + "|" + getSpectatorsCount() + ")");
        sender.sendMessage("Alive players: " + getAlivePlayers().stream().map(GamePlayer::getPlayer).map(Player::getName).collect(Collectors.joining(", ")));
        sender.sendMessage("Spectator players: " + getSpectators().stream().map(GamePlayer::getPlayer).map(Player::getName).collect(Collectors.joining(", ")));
        sender.sendMessage("§m-----------------------------");
    }

}