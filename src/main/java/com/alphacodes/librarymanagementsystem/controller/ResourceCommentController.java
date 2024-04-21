package com.alphacodes.librarymanagementsystem.controller;

import com.alphacodes.librarymanagementsystem.DTO.ResourceCommentDto;
import com.alphacodes.librarymanagementsystem.service.ResourceCommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user/{userID}/resource")
public class ResourceCommentController {
    private final ResourceCommentService resourceCommentService;

    public ResourceCommentController(ResourceCommentService resourceCommentService) {
        this.resourceCommentService = resourceCommentService;
    }

    // Add a new comment to a resource
    @PostMapping("/{rID}/comment")
    public ResponseEntity<ResourceCommentDto> addResourceComment(@PathVariable int userID,@PathVariable Long rID, @RequestBody ResourceCommentDto resourceCommentDto) {
        return new ResponseEntity<>(resourceCommentService.addResourceComment(userID, rID, resourceCommentDto), HttpStatus.CREATED);
    }

    // Get all comments for a resource
    @GetMapping("/{rID}/comment")
    public List<ResourceCommentDto> getAllResourceComments(@PathVariable long rID) {
        return resourceCommentService.getAllResourceComments(rID);
    }

    // Get a comment by its ID
    @GetMapping("/{rID}/comment/{rcmID}")
    public ResponseEntity<ResourceCommentDto> getResourceCommentById(@PathVariable Long rcmID, @PathVariable Long rID) {
        ResourceCommentDto resourceCommentDto = resourceCommentService.getResourceCommentById(rID,rcmID);
        return new ResponseEntity<>(resourceCommentDto, HttpStatus.OK);
    }

    // Delete a comment by its ID
    @DeleteMapping("/{rID}/comment/{rcmID}")
    public ResponseEntity<String> deleteResourceComment(@PathVariable Long rcmID, @PathVariable Long rID) {
        return new ResponseEntity<>(resourceCommentService.deleteResourceComment(rID, rcmID),HttpStatus.NO_CONTENT);
    }
}
