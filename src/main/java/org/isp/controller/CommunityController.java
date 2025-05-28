package org.isp.controller;

import org.isp.model.Community;
import org.isp.service.CommunityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/communities")
public class CommunityController {

    private final CommunityService communityService;

    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }

    // Get all communities
    @GetMapping
    public ResponseEntity<List<Community>> getAllCommunities() {
        List<Community> communities = communityService.getAllCommunities();
        return ResponseEntity.ok(communities);
    }

    // Get a community by ID
    @GetMapping("/{id}")
    public ResponseEntity<Community> getCommunityById(@PathVariable Long id) {
        Community community = communityService.getCommunityById(id);
        return ResponseEntity.ok(community);
    }

    // Create a new community
    @PostMapping
    public ResponseEntity<Community> createCommunity(@Valid @RequestBody Community community) {
        Community savedCommunity = communityService.saveCommunity(community);
        return new ResponseEntity<>(savedCommunity, HttpStatus.CREATED);
    }

    // Create multiple communities
    @PostMapping("/batch")
    public ResponseEntity<List<Community>> createCommunities(@Valid @RequestBody List<Community> communities) {
        List<Community> savedCommunities = communityService.saveCommunities(communities);
        return new ResponseEntity<>(savedCommunities, HttpStatus.CREATED);
    }

    // Delete a community by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommunity(@PathVariable Long id) {
        communityService.deleteCommunity(id);
        return ResponseEntity.noContent().build();
    }

    // Search communities by name
    @GetMapping("/search/name")
    public ResponseEntity<List<Community>> searchCommunitiesByName(@RequestParam String name) {
        List<Community> communities = communityService.findCommunitiesByName(name);
        return ResponseEntity.ok(communities);
    }

    // Search communities by location containing a keyword
    @GetMapping("/search/location")
    public ResponseEntity<List<Community>> searchCommunitiesByLocation(@RequestParam String keyword) {
        List<Community> communities = communityService.findCommunitiesByLocationContaining(keyword);
        return ResponseEntity.ok(communities);
    }
}
