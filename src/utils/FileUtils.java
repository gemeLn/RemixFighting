package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

	public List<String> readLinesFromFile(String file) {
		List<String> lines = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(file)));
			String line;
			String f1;
			while ((line = br.readLine()) != null) {
				f1 = line.substring(0, 1);
				if (!f1.equals("/")) {
					lines.add(line);
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}

}
