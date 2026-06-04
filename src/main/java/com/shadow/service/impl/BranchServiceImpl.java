package com.shadow.service.impl;

import com.shadow.exception.ResourceNotFoundException;
import com.shadow.mapper.BranchMapper;
import com.shadow.model.Branch;
import com.shadow.model.Store;
import com.shadow.model.User;
import com.shadow.payload.dto.BranchDTO;
import com.shadow.repository.BranchRepository;
import com.shadow.service.BranchService;
import com.shadow.service.StoreService;
import com.shadow.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {
    private final BranchRepository branchRepository;
    private final UserService userService;
    private final StoreService storeService;
    private final BranchMapper branchMapper;

    @Override
    public BranchDTO createBranch(BranchDTO branchDTO) {
        Store store = storeService.getStoreByAdmin();

        User currentUser = userService.getCurrentUser();

        Branch branch = branchMapper.toEntity(branchDTO);
        branch.setStore(store);
        branch.setManager(currentUser);
        Branch savedBranch = branchRepository.save(branch);

        return branchMapper.toDTO(savedBranch);
    }

    @Override
    public BranchDTO updateBranch(Long id, BranchDTO branchDTO) {
        Branch existing = branchRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Branch", id)
        );

        existing.setName(branchDTO.getName());
        existing.setAddress(branchDTO.getAddress());
        existing.setPhone(branchDTO.getPhone());
        existing.setEmail(branchDTO.getEmail());
        existing.setWorkingDays(branchDTO.getWorkingDays());
        existing.setOpenTime(branchDTO.getOpenTime());
        existing.setCloseTime(branchDTO.getCloseTime());
        Branch updatedBranch = branchRepository.save(existing);

        return branchMapper.toDTO(updatedBranch);
    }

    @Override
    public void deleteBranch(Long id) {
        Branch existing = branchRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Branch", id)
        );

        branchRepository.delete(existing);
    }

    @Override
    public List<BranchDTO> getBranchByStoreId(Long storeId) {
        List<Branch> branches = branchRepository.findByStoreId(storeId);
        return branches.stream().map(branchMapper::toDTO).toList();
    }

    @Override
    public BranchDTO getBranchById(Long id) {
        Branch existing = branchRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Branch", id)
        );
        return branchMapper.toDTO(existing);
    }
}
