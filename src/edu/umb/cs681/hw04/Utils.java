package edu.umb.cs681.hw04;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utils {
    private Utils() {}

    public static List<HousingDataset> parseCSV() {
        List<HousingDataset> hDList = new ArrayList<>();
        Path path = Paths.get("assets/bos-housing.csv");
        try (Stream<String> lines = Files.lines(path)) {
            List<List<String>> csv = lines.skip(1)
                    .map(line -> Stream.of(line.split(",")).collect(Collectors.toList()))
                    .collect(Collectors.toList());

            csv.stream().forEach(row -> {
                hDList.add(new HousingDataset(Double.parseDouble(row.get(0)),
                        Double.parseDouble(row.get(1)), Double.parseDouble(row.get(2)),
                        Boolean.parseBoolean(row.get(3)), Double.parseDouble(row.get(4)),
                        Double.parseDouble(row.get(5)), Double.parseDouble(row.get(6)),
                        Double.parseDouble(row.get(7)), Integer.parseInt(row.get(8)),
                        Integer.parseInt(row.get(9)), Double.parseDouble(row.get(10)),
                        Double.parseDouble(row.get(11)), Double.parseDouble(row.get(12)),
                        Double.parseDouble(row.get(13))));
            });

        } catch (IOException ex) {
            // handle exception
        }


        return hDList;
    }

    public static List<HousingDataset> getTopTenPercentCrimPtratio(List<HousingDataset> housingDatasets) {
        int size = (int) (0.1 * housingDatasets.size());

        return housingDatasets.stream()
                .sorted(Comparator.comparing(HousingDataset::getCrim)
                        .thenComparing(HousingDataset::getPtratio))
                .limit(size).collect(Collectors.toList());

    }

    public static List<HousingDataset> getTopTenPercentLeastPop(List<HousingDataset> housingDatasets) {
        int size = (int) (0.1 * housingDatasets.size());

        return housingDatasets.stream()
                .sorted(Comparator.comparing(HousingDataset::getB))
                .limit(size).collect(Collectors.toList());

    }

    public static List<HousingDataset> getTopTenPercentHighestTax(List<HousingDataset> housingDatasets) {
        int size = (int) (0.1 * housingDatasets.size());

        return housingDatasets.stream()
                .sorted(Comparator.comparing(HousingDataset::getB).reversed())
                .limit(size).collect(Collectors.toList());
    }
}
