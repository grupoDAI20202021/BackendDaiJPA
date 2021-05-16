package daibackend.demo.controller;

import daibackend.demo.model.Image;
import daibackend.demo.model.Sponsor;
import daibackend.demo.payload.response.ApiResponse;
import daibackend.demo.repository.ImageRepository;
import daibackend.demo.repository.SponsorRepository;
import daibackend.demo.security.CurrentUser;
import daibackend.demo.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@RestController
@RequestMapping(value = "/api")
public class ImageController {

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    SponsorRepository sponsorRepository;

    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TOWNHALL') or hasRole('INSTITUTION')")
    @GetMapping(path = { "/photos/{imageId}" })
    public Image getImage(@PathVariable("imageId") Long imageName) throws IOException {

        final Optional<Image> retrievedImage = imageRepository.findById(imageName);
        Image img = new Image(null, retrievedImage.get().getName(), retrievedImage.get().getType(),
                decompressBytes(retrievedImage.get().getPicByte()));
        return img;
    }

    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TOWNHALL') ")
    @PutMapping(value = "/sponsors/upload-photos/{idSponsor}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> updateUserPhoto(@PathVariable(value = "idSponsor") Long idSponsor,
                                                       @RequestParam("file") MultipartFile file, @CurrentUser UserPrincipal currentUser) {
        try {
            Sponsor sponsor = sponsorRepository.findDistinctByIdSponsor(idSponsor);
            // File Validations
            String fileType = file.getContentType();
            Long fileSize = file.getSize();

            if (!((fileType.equals("image/png")) || (fileType.equals("image/jpeg")))) {
                return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Image can only be png or jpg"),
                        HttpStatus.BAD_REQUEST);
            }

            if (fileSize > 1000000) {
                return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Image can't exceeds 1MB"),
                        HttpStatus.BAD_REQUEST);
            }
            // End of Validations

            // File Name
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-z");
            String strDate = formatter.format(date);
            String id = Long.toString(idSponsor);
            String fileName = "profilePhoto-" + id + "-" + strDate + ".png";
            //
            imageRepository.deleteSponsorPhoto(idSponsor);
            Image img = new Image(null, fileName, fileType, compressBytes(file.getBytes()));
            imageRepository.save(img);
            Long imgId = img.getId();
            sponsorRepository.updateSponsorPhotoId(imgId,idSponsor);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Image updated successfully", imgId),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    // compress the image bytes before storing it in the database
    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }

        return outputStream.toByteArray();
    }
    // uncompress the image bytes before returning it to the angular application
    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }


}
