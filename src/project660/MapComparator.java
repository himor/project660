package project660;

import java.util.Comparator;
import java.util.Map;

public class MapComparator implements Comparator<String> {
    Map<String, FileInfo> map;

    public MapComparator(Map<String, FileInfo> m) {
        map = m;
    }

    @Override
    public int compare(String arg0, String arg1) {
        return map.get(arg0).getFilename().compareTo(map.get(arg1).getFilename());
    }

}
