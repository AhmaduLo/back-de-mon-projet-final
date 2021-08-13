package com.example.shambles.utils;


import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

public class FileUtilePoster {

	/*-----chemin ou on stock nos image------------*/
	static String uploadDir = "src/main/webapp/WEB-INF/photoPost/";
	
	public static long saveFileAndReplace(String lastFile, MultipartFile file, String newFile, Long id) throws IOException{
		Path uploadPath = Paths.get(uploadDir+"/"+id);
	//	 /---si le uploadpath n'existe pas il le creer--------/
		if(!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		try {
			if(lastFile != null) {
				   //si le lastFile n'est pas égale a la nouvelle file ==> on ecrase l'ancienne
				if(!lastFile.equals(newFile)) {
					Files.delete(uploadPath.resolve(lastFile));
				}
				
			}
			return Files.copy(file.getInputStream(),uploadPath.resolve(newFile),StandardCopyOption.REPLACE_EXISTING);
		}catch (IOException e) {
            e.printStackTrace();
        }
		return 0;
		
	}
	
	public static void saveFile(long demoId, String fileName,  MultipartFile multipartFile) throws IOException{
		Path uploadPath = Paths.get(uploadDir + "/" + demoId);
		/*-------si le dosier n'existe pas il le creer----------------*/
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
			}
		try (InputStream inputStream = multipartFile.getInputStream()) {
			/*----si le dosier existe il fait la methode resolve----------*/
		Path filePath = uploadPath.resolve(fileName);

		Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		}catch (IOException ioe) {
			throw new IOException("Could not save image file: " + fileName);
		}
		
	}
	
}

