package sg.edu.nus.iss.miniproject.services;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class ImageService {
    
    @Autowired
    private AmazonS3 s3;

     public String uploadFile(MultipartFile file) {

      String id = UUID.randomUUID().toString().substring(0, 8);


        ObjectMetadata metaData = new ObjectMetadata();
             metaData.setContentType(file.getContentType());
             metaData.setContentLength(file.getSize());
             metaData.addUserMetadata("name", file.getOriginalFilename());
             System.out.println(file.getOriginalFilename());

             try  {
            System.out.println("trying to put files");
             PutObjectRequest putReq = new PutObjectRequest("miniproject", id, file.getInputStream(), 
             metaData);
             putReq = putReq.withCannedAcl(CannedAccessControlList.PublicRead);
             try {
                 s3.putObject(putReq);
                System.out.println("Upload successful");
             } catch(Exception ex)  {
               System.out.println("nope");
                ex.printStackTrace();
             }

             } catch(IOException ex) {
                ex.printStackTrace();
             }
              
             return id;
    }

    public String getUrl(String id) {
      return s3.getUrl("miniproject", id).toExternalForm();
    }

}
