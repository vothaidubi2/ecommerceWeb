package com.asm.admin.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.asm.service.ImageService;

import java.util.List;

@RestController
@RequestMapping("/admin/api/image")
public class AdminImageRestController {
	@Autowired
	ImageService imgService;

	@PostMapping("/upload")
	public ResponseEntity<List<String>> uploadImages(@RequestParam("files") List<MultipartFile> files) {
		return imgService.uploadImages(files);
	}
}
