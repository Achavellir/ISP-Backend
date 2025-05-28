package org.isp.service;

import org.isp.exception.ResourceNotFoundException;
import org.isp.model.Community;
import org.isp.repository.CommunityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunityService {

    private final CommunityRepository communityRepository;

    public CommunityService(CommunityRepository communityRepository) {
        this.communityRepository = communityRepository;
    }

    // Get all communities
    public List<Community> getAllCommunities() {
        return communityRepository.findAll();
    }

    // Get a community by ID
    public Community getCommunityById(Long id) {
        return communityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Community not found with id: " + id));
    }

    // Save a new community
    public Community saveCommunity(Community community) {
        return communityRepository.save(community);
    }

    // Save multiple communities
    public List<Community> saveCommunities(List<Community> communities) {
        return communityRepository.saveAll(communities);
    }

    // Delete a community by ID
    public void deleteCommunity(Long id) {
        communityRepository.deleteById(id);
    }

    // Custom query: Find communities by name
    public List<Community> findCommunitiesByName(String name) {
        return communityRepository.findByName(name);
    }

    // Custom query: Find communities by location containing a keyword
    public List<Community> findCommunitiesByLocationContaining(String keyword) {
        return communityRepository.findByLocationContaining(keyword);
    }
}
