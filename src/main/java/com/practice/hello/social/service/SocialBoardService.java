package com.practice.hello.social.service;


import com.practice.hello.freeboard.entity.FreeBoard;
import com.practice.hello.image.entity.Image;
import com.practice.hello.image.repository.ImageRepository;
import com.practice.hello.image.service.S3Service;
import com.practice.hello.member.entity.Member;
import com.practice.hello.member.repository.MemberRepository;
import com.practice.hello.social.dto.SocialBoardCreateDTO;
import com.practice.hello.social.entity.SocialBoard;
import com.practice.hello.social.entity.SocialComment;
import com.practice.hello.social.repository.SocialBoardRepository;
import com.practice.hello.social.repository.SocialCommentRepository;
import com.practice.hello.social.repository.SocialReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service // Service annotation
public class SocialBoardService {


    private final SocialBoardRepository socialBoardRepository;
    private final SocialCommentRepository socialCommentRepository;

    private final MemberRepository memberRepository;
    private final SocialReplyRepository socialReplyRepository;
    private final ImageRepository imageRepository;
    private final S3Service s3Service;

    @Transactional
    public void deleteBoardAndAdjustIds(Long id, String uId) {
        // First delete all comments associated with the board
        Member member = memberRepository.findByUid(uId)
                .orElseThrow(() -> new IllegalArgumentException("이 이메일에 해당하는 사람없어용"));


        SocialBoard socialBoard = socialBoardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 id에 해당하는 게시글 없어용"));
        if (!socialBoard.getMember().getUid().equals(uId)) {
            throw new IllegalArgumentException("본인 게시글만 삭제 가능해용");
        }
        // First, delete all replies associated with each comment of the board
        List<SocialComment> socialComments = socialCommentRepository.findByBoardId(id);
        for (SocialComment socialComment : socialComments) {
            socialReplyRepository.deleteAllBySocialCommentId(socialComment.getId());
        }
        // 이미지가 있으면 S3에서 삭제
        Image image = socialBoard.getImage();
        if (image != null) {
            String key = image.getImageUrl().substring(image.getImageUrl().lastIndexOf("/") + 1);
            s3Service.deleteFile(key);
            imageRepository.delete(image);
        }

        // Now delete all comments associated with the board
        socialCommentRepository.deleteAll(socialComments);

        // Finally, delete the board
        socialBoardRepository.deleteById(id);
        socialBoardRepository.flush();

    }
    public SocialBoard saveBoard(SocialBoardCreateDTO dto, String uId, MultipartFile file) {


        Member member = memberRepository.findByUid(uId)
                .orElseThrow(() -> new IllegalArgumentException("이 이메일에 해당하는 사람없어용"));
        String imageUrl = null;
        if (file != null && !file.isEmpty()) {
            try {
                imageUrl = s3Service.uploadFile(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Image image = imageUrl != null ? imageRepository.save(new Image(imageUrl)) : null;
        // 이미지가 dto에 없이 담겨서 왔다면 null로 db에 설정 아니라면 s3버켓에 넣고 설정

        SocialBoard socialBoard = dto.toEntity(member,image);


        socialBoardRepository.save(socialBoard);

        return socialBoard;
    }


    //update시에는 entity 형태로 받아서 저장해야 한다
    public SocialBoard saveBoard(SocialBoard socialBoard) {
        return socialBoardRepository.save(socialBoard);
    }


    public Optional<SocialBoard> getBoardById(Long id){
        return socialBoardRepository.findById(id);
    }

    public List<SocialBoard> readBoardAll(){
        return socialBoardRepository.findAll();
    }

    public void deleteBoard(Long id){
        socialBoardRepository.deleteById(id);
    }


    public SocialBoard likeBoard(Long id) {
        SocialBoard socialBoard = socialBoardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found"));

        socialBoard.setLikeStatus(true); // Set like status to true
        return socialBoardRepository.save(socialBoard);
    }

    @Transactional
    public SocialBoard unlikeBoard(Long id) {
        SocialBoard socialBoard = socialBoardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found"));

        socialBoard.setLikeStatus(false); // Set like status to false
        return socialBoardRepository.save(socialBoard);
    }

    @Transactional
    public SocialBoard updateBoard(Long id, SocialBoardCreateDTO dto,String uId,MultipartFile file) {

        Member member = memberRepository.findByUid(uId)
                .orElseThrow(() -> new IllegalArgumentException("이 계정에 해당하는 사람없어용"));
        SocialBoard socialBoard = socialBoardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 id에 해당하는 게시글 없어용"));

        if (!socialBoard.getMember().getUid().equals(uId)) {
            throw new IllegalArgumentException("본인 게시글만 수정 가능해용");
        }

        Image image = socialBoard.getImage();
        if (file != null && !file.isEmpty()) {
            if (image != null) {
                String key = socialBoard.getImage().getImageUrl().substring(socialBoard.getImage().getImageUrl().lastIndexOf("/") + 1);
                s3Service.deleteFile(key);
                imageRepository.delete(image);
            }
            String imageUrl = null;
            try {
                imageUrl = s3Service.uploadFile(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            image = imageRepository.save(new Image(imageUrl));
        } else if (image != null) {
            imageRepository.delete(image);
            image = null;
        }
        socialBoard.update(dto.title(), dto.content());
        socialBoard.setImage(image);
        return socialBoardRepository.save(socialBoard);
    }

    public Page<SocialBoard> readBoardAll(Pageable pageable) {
        return socialBoardRepository.findAll(pageable);
    }

}
