package com.practice.hello.secretboard.service;



import com.practice.hello.freeboard.entity.FreeBoard;
import com.practice.hello.image.entity.Image;
import com.practice.hello.image.repository.ImageRepository;
import com.practice.hello.image.service.S3Service;
import com.practice.hello.member.entity.Member;
import com.practice.hello.member.repository.MemberRepository;
import com.practice.hello.secretboard.dto.SecretBoardCreateDTO;
import com.practice.hello.secretboard.entity.SecretBoard;
import com.practice.hello.secretboard.entity.SecretComment;
import com.practice.hello.secretboard.repository.SecretBoardRepository;
import com.practice.hello.secretboard.repository.SecretCommentRepository;
import com.practice.hello.secretboard.repository.SecretReplyRepository;
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
public class SecretBoardService {

    private final MemberRepository memberRepository;
    private final SecretBoardRepository secretBoardRepository;
    private final SecretCommentRepository secretCommentRepository;


    private final SecretReplyRepository secretReplyRepository;
    private final ImageRepository imageRepository;
    private final S3Service s3Service;
    @Transactional
    public void deleteBoardAndAdjustIds(Long id, String uId) {
        // First delete all comments associated with the board

        Member member = memberRepository.findByUid(uId)
                .orElseThrow(() -> new IllegalArgumentException("이 이메일에 해당하는 사람없어용"));

        SecretBoard secretBoard = secretBoardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 id에 해당하는 게시글 없어용"));
        if (!secretBoard.getMember().getUid().equals(uId)) {
            throw new IllegalArgumentException("본인 게시글만 삭제 가능해용");
        }
        // First, delete all replies associated with each comment of the board
        List<SecretComment> secretComments = secretCommentRepository.findByBoardId(id);
        for (SecretComment secretComment : secretComments ) {
            secretReplyRepository.deleteAllBySecretCommentId(secretComment.getId());
        }
        // 이미지가 있으면 S3에서 삭제
        Image image = secretBoard.getImage();
        if (image != null) {
            String key = image.getImageUrl().substring(image.getImageUrl().lastIndexOf("/") + 1);
            s3Service.deleteFile(key);
            imageRepository.delete(image);
        }

        // Now delete all comments associated with the board
        secretCommentRepository.deleteAll(secretComments);

        // Finally, delete the board
        secretBoardRepository.deleteById(id);
        secretBoardRepository.flush();

    }
    public SecretBoard saveBoard(SecretBoardCreateDTO dto, String uId,  MultipartFile file) {
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

        SecretBoard secretBoard = dto.toEntity(member,image);




        return secretBoardRepository.save(secretBoard);
    }


    //update시에는 entity 형태로 받아서 저장해야 한다
    public SecretBoard saveBoard(SecretBoard secretBoard) {
        return secretBoardRepository.save(secretBoard);
    }


    public Optional<SecretBoard> getBoardById(Long id){
        return secretBoardRepository.findById(id);
    }

    public List<SecretBoard> readBoardAll(){
        return secretBoardRepository.findAll();
    }

    public void deleteBoard(Long id){
        secretBoardRepository.deleteById(id);
    }


    public SecretBoard likeBoard(Long id) {
        SecretBoard freeBoard = secretBoardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found"));

        freeBoard.setLikeStatus(true); // Set like status to true
        return secretBoardRepository.save(freeBoard);
    }

    @Transactional
    public SecretBoard unlikeBoard(Long id) {
        SecretBoard freeBoard = secretBoardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found"));

        freeBoard.setLikeStatus(false); // Set like status to false
        return secretBoardRepository.save(freeBoard);
    }

    @Transactional
    public SecretBoard updateBoard(Long id, SecretBoardCreateDTO dto,String uId, MultipartFile file) {
        Member member = memberRepository.findByUid(uId)
                .orElseThrow(() -> new IllegalArgumentException("이 계정에 해당하는 사람없어용"));
        SecretBoard secretBoard = secretBoardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found"));
        if (!secretBoard.getMember().getUid().equals(uId)) {
            throw new IllegalArgumentException("본인 게시글만 수정 가능해용");
        }


        Image image = secretBoard.getImage();
        if (file != null && !file.isEmpty()) {
            if (image != null) {
                String key = secretBoard.getImage().getImageUrl().substring(secretBoard.getImage().getImageUrl().lastIndexOf("/") + 1);
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
        secretBoard.update(dto.title(), dto.content());
        secretBoard.setImage(image);
        return secretBoardRepository.save(secretBoard);
    }

    public Page<SecretBoard> readBoardAll(Pageable pageable) {
        return secretBoardRepository.findAll(pageable);
    }

}
