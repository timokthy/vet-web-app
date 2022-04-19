package group825.vetapp2.photos;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


/**
 * Service that performs Photo requests
 *
 * @author Jimmy Zhu
 * @version 2.0
 * @since Dec 6, 2021
 */
@Service
public class PhotosService {

	/**
	 * Photo repository that accesses the database
	 */
	private final PhotosRepository dbPhotos;
	
	/**
	 * Constructor that initializes the PhotoService
	 * @param dbPhotos repository denoted with Qualifier annotation storing the Photo objects
	 */
	@Autowired
	public PhotosService(@Qualifier("tempPhotosRepo") PhotosRepository dbPhotos) {
		this.dbPhotos = dbPhotos;
	}

	
	/**
	 * Inserts a photo in the database
	 * @param photo = new Photo object to be added
	 * @return integer verifying successful code execution
	 * @throws Exception when there is an SQL Exception
	 */
	public int insertPhoto(Photo photo ) throws Exception {
		return dbPhotos.insertPhoto(photo);
	}
	
	
	/**
	 * Selects photos from the database by the animal ID number
	 * @param animalID = id pertaining to specific animal
	 * @return List<Photo> either containing the list of Photo objects for particular animal or is empty
	 * @throws Exception when there is an SQL Exception
	 */
	public List<Photo> selectPhotosByID(int animalID) throws Exception {
		ArrayList<Photo> results = dbPhotos.selectPhotosByID(animalID);
		return results;
	}
	
	/**
	 * Deletes a photo from the database by photo ID number
	 * @param photoID = id pertaining to specific photo for an animal
	 * @return integer verifying successful code execution
	 * @throws Exception when there is an SQL Exception
	 */
	public int deletePhotoByID(int photoID) throws Exception {
		String pathToDelete = "../../Front End/public"+dbPhotos.getFilePath(photoID);
		
		System.out.println("pathToDelete = " + pathToDelete);
		Path oldPath = Paths.get(pathToDelete);
		boolean result = Files.deleteIfExists(oldPath);
		
		return dbPhotos.deletePhotoByID(photoID);
	}
	
	
	
	 /**
     * Save a photo in the respective animal's folder in the desired directory and record the photo information (including filepath) in the database
     * @param multipartFile = holds the image
     * @param animalID = animal ID
     * @return 1 if successful
     * @throws IOException when the image fails to be saved or a folder fails to be created
     */
    public int savePhoto(MultipartFile multipartFile, int animalID, int userID) throws IOException {
    	
    	//get the filename which is currently stored in a path inside the "original filename"
    	String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
    	
    	//desired directory location to save the photos 
    	String savedDir = "../../Front End/public/photos/"+animalID+"/";
    	
    	//convert savedDir to a Path object for concatenation later
    	Path newPath = Paths.get(savedDir);
    	
    	int responseCheck = 0;
    	
    	//Copy the image that was uploaded to desired file location
    	try(InputStream inputStream = multipartFile.getInputStream()){
    		//create the directory if the directory does not already exist (ie for one specific animal)
    		if (!Files.exists(newPath)) {
        		Files.createDirectories(newPath);
        	}
    		//concatenate the filename with the directory path
    		Path filePath = newPath.resolve(fileName);
    		System.out.println("New file to be saved at: "+filePath.toFile().getAbsolutePath());
    		
    		//copy the file to the desired folder
    		Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
    		
    		String filePathDB = "/photos/"+animalID+"/"+fileName;
    		String currDateTime = getTimestamp();
    		Photo newPhoto = new Photo(animalID, 1, filePathDB, userID, currDateTime);
    		
    		//check if this filepath already exists
    		//if exists then update    		
    		ArrayList<Photo> results = dbPhotos.selectPhotosByID(animalID);
    		for (Photo aPhoto: results) {
    			System.out.println("");
//    			if (aPhoto.getFilepath() == filePathDB) {
    			if (aPhoto.getFilepath().equals(filePathDB)) {
    				System.out.println("Filepath already exists on DB. Updating existing photo's information.");
    				responseCheck = dbPhotos.updatePhotoByID(aPhoto.getPhotoID(), newPhoto);
    				return responseCheck;
    			}
    		}
    		
    		//If this is a new photo then add the new photo
    		responseCheck = dbPhotos.insertPhoto(newPhoto);
    		
    	}catch (Exception e) {
    		System.out.println("Error creating new directory or copying the file or saving the new photo object's information in the database");
    		e.printStackTrace();
    	}
    	
    	return responseCheck;
    }
    
    /**
     * Get a time stamp of when this method is called which is the same time that the photo is uploaded
     * @return String containing the time stamp for when the photo is uploaded
     */
    private String getTimestamp() {
    	Calendar cal = Calendar.getInstance();

		cal.add(Calendar.DATE, 0);

		Date date = cal.getTime();             

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");          

		String currentDateTime = null;

		try {

			currentDateTime = format1.format(date);

		    System.out.println(currentDateTime );

		} catch (Exception e1) {

		    // TODO Auto-generated catch block

		    e1.printStackTrace();

		}
		return currentDateTime;
    }
	
}
