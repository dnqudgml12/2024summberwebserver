package com.practice.hello.advertise.service;


import com.practice.hello.advertise.dto.AdvertiseBoardCreateDTO;
import com.practice.hello.advertise.entity.AdvertiseBoard;
import com.practice.hello.advertise.entity.AdvertiseComment;
import com.practice.hello.advertise.repository.AdvertiseBoardRepository;
import com.practice.hello.advertise.repository.AdvertiseCommentRepository;
import com.practice.hello.advertise.repository.AdvertiseReplyRepository;
import com.practice.hello.member.entity.Member;
import com.practice.hello.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service // Service annotation
@Slf4j
public class AdvertiseBoardService {


    private final AdvertiseBoardRepository advertiseBoardRepository;
    private final AdvertiseCommentRepository advertiseCommentRepository;

    private final MemberRepository memberRepository;
    private final AdvertiseReplyRepository advertiseReplyRepository;

    @Transactional
    public void deleteBoardAndAdjustIds(Long id, String uId) {
        // First delete all comments associated with the board

        Member member = memberRepository.findByUid(uId)
                .orElseThrow(() -> new IllegalArgumentException("이 이메일에 해당하는 사람없어용"));


        AdvertiseBoard advertiseBoard = advertiseBoardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 id에 해당하는 게시글 없어용"));

        if (!advertiseBoard.getMember().getUid().equals(uId)) {
            throw new IllegalArgumentException("본인 게시글만 삭제 가능해용");
        }
        // First, delete all replies associated with each comment of the board
        List<AdvertiseComment> advertiseComments = advertiseCommentRepository.findByBoardId(id);
        for (AdvertiseComment advertiseComment : advertiseComments) {
            advertiseReplyRepository.deleteAllByAdvertiseCommentId(advertiseComment.getId());
        }

        // Now delete all comments associated with the board
        advertiseCommentRepository.deleteAll(advertiseComments);

        // Finally, delete the board
        advertiseBoardRepository.deleteById(id);
        advertiseBoardRepository.flush();

    }
    public AdvertiseBoard saveBoard(AdvertiseBoardCreateDTO dto,String uId) {

        log.info("Creating post for user: {}", uId);
        Member member = memberRepository.findByUid(uId)
                .orElseThrow(() -> new IllegalArgumentException("이 이메일에 해당하는 사람없어용"));

        AdvertiseBoard advertiseBoard = dto.toEntity(member);


        advertiseBoardRepository.save(advertiseBoard);


        return advertiseBoard;
    }


    //update시에는 entity 형태로 받아서 저장해야 한다
    public AdvertiseBoard saveBoard(AdvertiseBoard advertiseBoard) {
        return advertiseBoardRepository.save(advertiseBoard);
    }


    public Optional<AdvertiseBoard> getBoardById(Long id){
        return advertiseBoardRepository.findById(id);
    }

    public List<AdvertiseBoard> readBoardAll(){
        return advertiseBoardRepository.findAll();
    }

    public void deleteBoard(Long id){
        advertiseBoardRepository.deleteById(id);
    }


    public AdvertiseBoard likeBoard(Long id) {
        AdvertiseBoard advertiseBoard = advertiseBoardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found"));

        advertiseBoard.setLikeStatus(true); // Set like status to true
        return advertiseBoardRepository.save(advertiseBoard);
    }

    @Transactional
    public AdvertiseBoard unlikeBoard(Long id) {
        AdvertiseBoard advertiseBoard = advertiseBoardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found"));

        advertiseBoard.setLikeStatus(false); // Set like status to false
        return advertiseBoardRepository.save(advertiseBoard);
    }

    @Transactional
    public AdvertiseBoard updateBoard(Long id, AdvertiseBoardCreateDTO dto,String uId) {
        Member member = memberRepository.findByUid(uId)
                .orElseThrow(() -> new IllegalArgumentException("이 계정에 해당하는 사람없어용"));

        AdvertiseBoard advertiseBoard = advertiseBoardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 id에 해당하는 게시글 없어용"));

        if (!advertiseBoard .getMember().getUid().equals(uId)) {
            throw new IllegalArgumentException("본인 게시글만 수정 가능해용");
        }
        advertiseBoard.update(dto.title(), dto.content());
        return advertiseBoardRepository.save(advertiseBoard);
    }

    public Page<AdvertiseBoard> readBoardAll(Pageable pageable) {
        return advertiseBoardRepository.findAll(pageable);
    }

}
