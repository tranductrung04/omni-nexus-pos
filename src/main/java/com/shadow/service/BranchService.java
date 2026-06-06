package com.shadow.service;

import com.shadow.model.Branch;
import com.shadow.payload.dto.BranchDTO;

import java.util.List;

public interface BranchService {
    BranchDTO createBranch(BranchDTO branchDTO);

    BranchDTO updateBranch(Long id, BranchDTO branchDTO);

    void deleteBranch(Long id);

    List<BranchDTO> getAllBranchesByStoreId(Long storeId);

    BranchDTO getBranchById(Long id);

    Branch getBranchEntityById(Long id);
}
