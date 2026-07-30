package kr.co.ictedu.projectBack.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.ictedu.projectBack.common.PagingService;
import kr.co.ictedu.projectBack.service.CommunityService;
import kr.co.ictedu.projectBack.vo.CommunityVO;
import kr.co.ictedu.projectBack.vo.PageVO;

@RestController
@RequestMapping("/api/community")
public class CommunityController {

    @Autowired
    private CommunityService comm;

    @Autowired
    private PagingService pagingService;

    // application.properties에서 설정된 저장 경로
    @Value("${spring.servlet.multipart.location}")
    private String filePath;

    @GetMapping("/coPath")
    public String getMethodName() {
        System.out.println("Path : " + filePath);
        return filePath;
    }

    @PostMapping("/commAdd")
    public ResponseEntity<?> communityAdd(CommunityVO vo, HttpServletRequest request) {

        MultipartFile mf = vo.getMfile();

        // 이미지가 있는 경우만 저장
        if (mf != null && !mf.isEmpty()) {

            File dir = new File(filePath);

            if (!dir.exists()) {
                dir.mkdirs();
            }

            String oriFn = mf.getOriginalFilename();

            File f = new File(filePath, oriFn);

            try {
                mf.transferTo(f);
                vo.setCimgn(oriFn);

            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("파일 업로드 실패");
            }

        } else {
            // 이미지 없이 등록
            vo.setCimgn("");
        }

        // 게시글 저장
        comm.add(vo);

        return ResponseEntity.ok("게시글 등록 성공!");
    }

    @RequestMapping("/coList")
    public Map<String, Object> upCommunityList(@RequestParam Map<String, String> paramMap, HttpServletRequest request) {
        String cPage = paramMap.get("cPage");
        System.out.println("searchType : " + paramMap.get("searchType"));
        System.out.println("searchValue : " + paramMap.get("searchValue"));
        System.out.println("*************************");
        int totalCnt = comm.totalCount(paramMap);
        PageVO pageVO = pagingService.makePage(totalCnt, cPage);

        Map<String, String> map = new HashMap<>(paramMap);
        map.put("begin", String.valueOf(pageVO.getBeginPerPage()));
        map.put("end", String.valueOf(pageVO.getEndPerPage()));
        List<CommunityVO> list = comm.list(map);

        Map<String, Object> response = new HashMap<>();
        response.put("data", list);
        response.put("totalItems", pageVO.getTotalRecord());
        response.put("totalPages", pageVO.getTotalPage());
        response.put("currentPage", pageVO.getNowPage());
        response.put("startPage", pageVO.getStartPage());
        response.put("endPage", pageVO.getEndPage());

        return response;
    }

    @GetMapping("/coDetail")
    public CommunityVO communityDetail(@RequestParam("num") int num) {
        return comm.detail(num);
    }

    @PostMapping(
        value = "/coUpdate",
        consumes = "multipart/form-data"
    )
    public ResponseEntity<?> update(@ModelAttribute CommunityVO vo) {

        try {
            // 기존 게시글 조회 (조회수 증가 안 하는 메소드 사용)
            CommunityVO oldVO = comm.get(vo.getCnum());

            MultipartFile mf = vo.getMfile();

            if (mf != null && !mf.isEmpty()) {

                // 저장 폴더 확인 및 생성
                File dir = new File(filePath);

                if (!dir.exists()) {
                    dir.mkdirs();
                }

                // 새 파일명 생성(UUID)
                String oriFn = mf.getOriginalFilename();
                String ext = "";
                int idx = (oriFn != null) ? oriFn.lastIndexOf(".") : -1;

                if (idx != -1) {
                    ext = oriFn.substring(idx);
                }

                String saveName = UUID.randomUUID().toString() + ext;

                // 새 파일 저장
                File newFile = new File(dir, saveName);
                mf.transferTo(newFile);

                // 상단에서 요청하신 파일 저장 완료 대기 로직 (0.3초)
                Thread.sleep(300);

                // 기존 이미지 삭제 (새 파일 저장 및 대기 후 삭제)
                if (oldVO != null
                        && oldVO.getCimgn() != null
                        && !oldVO.getCimgn().isBlank()) {

                    File oldFile = new File(filePath, oldVO.getCimgn());

                    if (oldFile.exists()) {
                        oldFile.delete();
                    }
                }

                // DB 저장을 위한 새 파일명 설정
                vo.setCimgn(saveName);

            } else {
                // 이미지 변경 안 했으면 기존 이미지 유지
                if (oldVO != null) {
                    vo.setCimgn(oldVO.getCimgn());
                }
            }

            // DB 수정 반영
            comm.update(vo);

            return ResponseEntity.ok("수정 완료");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("처리 중 대기 오류가 발생했습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("파일 수정 실패");
        }
    }

    @DeleteMapping("/coDelete")
    public ResponseEntity<?> communityDelete(@RequestParam("num") int num) {

        CommunityVO vo = comm.get(num);
        if (vo != null && vo.getCimgn() != null) {
            File file = new File(filePath, vo.getCimgn());

            if (file.exists()) {
                file.delete();
            }
        }
        comm.del(num);

        return ResponseEntity.ok("삭제 완료");
    }

}
