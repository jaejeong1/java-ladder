package ladder.domain.player;

import ladder.exception.NeedMorePlayerException;
import ladder.exception.NotFoundPlayerException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class Players {

    private static final int MIN_PERSON_NUM = 1;

    private final List<Player> playerList = new ArrayList<>();

    public Players(String playerNames) {
        String[] names = playerNames.split(",");

        validatePlayer(names);

        Arrays.stream(names).forEach(name -> playerList.add(new Player(name)));
    }

    private void validatePlayer(String[] names) {
        if (names.length == MIN_PERSON_NUM) {
            throw new NeedMorePlayerException("사다리 게임을 진행할 플레이어가 더 필요합니다");
        }
    }

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(playerList);
    }

    public int getPlayerNum() { return playerList.size(); }

    public int getPositionInLadder(String inputPlayer) {
        if(inputPlayer.equals("all")) {
            return playerList.size();
        }

        validatePlayer(inputPlayer);

        return IntStream.range(0, playerList.size())
                .filter(i -> playerList.get(i).isEqualPlayerName(inputPlayer))
                .findFirst()
                .orElse(0);
    }

    private void validatePlayer(String inputPlayer) {
        isContainPlayerName(inputPlayer);
    }

    private void isContainPlayerName(String inputPlayer) {
        long count = playerList.stream()
                .filter(player -> (player.toString().equals(inputPlayer)))
                .count();

        if (count == 0) {
            throw new NotFoundPlayerException("입력한 플레이어를 찾을 수 없습니다.");
        }
    }
}