package step1.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class Lines {

    private final List<Line> lines = new ArrayList<>();
    private final int height;

    public Lines(List<Line> lines, int height) {
        validateLines(lines, height);
        this.height = height;
        this.lines.addAll(lines);
    }

    public static Lines from(List<Line> lines) {
        return new Lines(lines, lines.get(0).getHeight());
    }

    public List<Line> getLines() {
        return Collections.unmodifiableList(lines);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return lines.size();
    }

    public boolean existsBridgeByIdxAndHeight(int idx, int h) {
        return lines.get(idx).getBridgeByIdx(h - 1).isOpen();
    }

    private void validateLines(List<Line> lines, int height) {
        validateHeightSame(lines, height);
        validateFirstLineIsAllFalse(lines);
        validateBridgesDuplicated(lines);
    }

    private void validateHeightSame(List<Line> lines, int height) {
        boolean isNotSame = lines.stream()
            .anyMatch(line -> !line.isEqualsHeight(height));

        if (isNotSame) {
            throw new IllegalArgumentException("Line의 모든 높이는 같아야합니다.");
        }
    }

    private void validateFirstLineIsAllFalse(List<Line> lines) {
        boolean allClosed = lines.get(0)
            .getBridges().stream()
            .anyMatch(Bridge::isOpen);

        if (allClosed) {
            throw new IllegalArgumentException("첫 번째 Line의 모든 Bridge는 False여야합니다.");
        }
    }

    private void validateBridgesDuplicated(List<Line> lines) {
        boolean isDuplicated = IntStream.range(1, lines.size())
            .anyMatch(idx -> lines.get(idx).isDuplicatedLine(lines.get(idx - 1)));

        if (isDuplicated) {
            throw new IllegalArgumentException("가로 라인에 인접한 Bridge는 같이 열려있을 수 없습니다.");
        }
    }
}