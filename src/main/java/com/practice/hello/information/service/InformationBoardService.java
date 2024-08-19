package com.practice.hello.information.service;


import com.practice.hello.freeboard.entity.FreeBoard;
import com.practice.hello.information.dto.InformationBoardCreateDTO;
import com.practice.hello.information.entity.InformationBoard;
import com.practice.hello.information.entity.InformationComment;
import com.practice.hello.information.repository.InformationBoardRepository;
import com.practice.hello.information.repository.InformationCommentRepository;
import com.practice.hello.information.repository.InformationReplyRepository;
import com.practice.hello.member.entity.Member;
import com.practice.hello.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service // Service annotation
public class InformationBoardService {


    private final InformationBoardRepository informationBoardRepository;
    private final InformationCommentRepository informationCommentRepository;

    private final MemberRepository memberRepository;
    private final InformationReplyRepository informationReplyRepository;

    @Transactional
    public void deleteBoardAndAdjustIds(Long id, String uId) {
        // First delete all comments associated with the board

        Member member = memberRepository.findByUid(uId)
                .orElseThrow(() -> new IllegalArgumentException("이 이메일에 해당하는 사람없어용"));
        // First, delete all replies associated with each comment of the board


        InformationBoard informationBoard = informationBoardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 id에 해당하는 게시글 없어용"));
        if (!informationBoard.getMember().getUid().equals(uId)) {
            throw new IllegalArgumentException("본인 게시글만 삭제 가능해용");
        }

        List<InformationComment> informationComments = informationCommentRepository.findByBoardId(id);
        for (InformationComment informationComment : informationComments) {
            informationReplyRepository.deleteAllByInformationCommentId(informationComment.getId());
        }

        // Now delete all comments associated with the board
        informationCommentRepository.deleteAll(informationComments);

        // Finally, delete the board
        informationBoardRepository.deleteById(id);
        informationBoardRepository.flush();

    }
    public InformationBoard saveBoard(InformationBoardCreateDTO dto, String uId) {

        Member member = memberRepository.findByUid(uId)
                .orElseThrow(() -> new IllegalArgumentException("이 이메일에 해당하는 사람없어용"));
        InformationBoard informationBoard = dto.toEntity(member);




        return  informationBoardRepository.save(informationBoard);
    }


    //update시에는 entity 형태로 받아서 저장해야 한다
    public InformationBoard saveBoard(InformationBoard informationBoard) {
        return informationBoardRepository.save(informationBoard);
    }


    public Optional<InformationBoard> getBoardById(Long id){
        return informationBoardRepository.findById(id);
    }

    public List<InformationBoard> readBoardAll(){
        return informationBoardRepository.findAll();
    }

    public void deleteBoard(Long id){
        informationBoardRepository.deleteById(id);
    }


    public InformationBoard likeBoard(Long id) {
        InformationBoard informationBoard = informationBoardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found"));

        informationBoard.setLikeStatus(true); // Set like status to true
        return informationBoardRepository.save(informationBoard);
    }

    @Transactional
    public InformationBoard unlikeBoard(Long id) {
        InformationBoard informationBoard = informationBoardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found"));

        informationBoard.setLikeStatus(false); // Set like status to false
        return informationBoardRepository.save(informationBoard);
    }

    @Transactional
    public InformationBoard updateBoard(Long id, InformationBoardCreateDTO dto,String uId) {
        Member member = memberRepository.findByUid(uId)
                .orElseThrow(() -> new IllegalArgumentException("이 계정에 해당하는 사람없어용"));
        InformationBoard informationBoard = informationBoardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 id에 해당하는 게시글 없어용"));
        if (!informationBoard.getMember().getUid().equals(uId)) {
            throw new IllegalArgumentException("본인 게시글만 수정 가능해용");
        }
        informationBoard.update(dto.title(), dto.content());
        return informationBoardRepository.save(informationBoard);
    }

    public Page<InformationBoard> readBoardAll(Pageable pageable) {
        return informationBoardRepository.findAll(pageable);
    }

}
