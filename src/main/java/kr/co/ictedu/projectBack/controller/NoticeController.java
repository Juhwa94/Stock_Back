package kr.co.ictedu.projectBack.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import jakarta.servlet.http.HttpServletRequest;
import kr.co.ictedu.projectBack.common.PagingService;
import kr.co.ictedu.projectBack.service.NoticeService;
import kr.co.ictedu.projectBack.vo.NoticeVO;
import kr.co.ictedu.projectBack.vo.PageVO;



@RestController
@RequestMapping("/api/notice")
public class NoticeController {


    @Autowired
    private NoticeService noti;


    @Autowired
    private PagingService pagingService;



    /*
     * 공지 등록
     */
    @PostMapping("/noAdd")
    public ResponseEntity<?> noAdd(NoticeVO vo){


        noti.add(vo);


        return ResponseEntity.ok("공지 등록 완료");

    }





    /*
     * 공지 목록
     */
    @GetMapping("/noList")
    public Map<String,Object> noticeList(
            @RequestParam Map<String,String> paramMap
    ){


        String cPage =
                paramMap.getOrDefault("cPage","1");



        int totalCnt =
                noti.totalCount(paramMap);



        PageVO pageVO =
                pagingService.makePage(
                        totalCnt,
                        cPage
                );



        Map<String,String> map =
                new HashMap<>(paramMap);



        map.put(
                "begin",
                String.valueOf(
                        pageVO.getBeginPerPage()
                )
        );


        map.put(
                "end",
                String.valueOf(
                        pageVO.getEndPerPage()
                )
        );



        List<NoticeVO> list =
                noti.list(map);



        Map<String,Object> response =
                new HashMap<>();


        response.put(
                "data",
                list
        );


        response.put(
                "totalItems",
                pageVO.getTotalRecord()
        );


        response.put(
                "totalPages",
                pageVO.getTotalPage()
        );


        response.put(
                "currentPage",
                pageVO.getNowPage()
        );


        response.put(
                "startPage",
                pageVO.getStartPage()
        );


        response.put(
                "endPage",
                pageVO.getEndPage()
        );



        return response;

    }





    /*
     * 공지 상세
     */
    @GetMapping("/noDetail")
    public NoticeVO noticeDetail(
            @RequestParam("num") int num
    ){


        return noti.detail(num);

    }






    /*
     * 공지 수정
     */
    @PostMapping("/noUpdate")
    public ResponseEntity<?> update(NoticeVO vo
    ){


        noti.update(vo);


        return ResponseEntity.ok(
                "공지 수정 완료"
        );

    }







    /*
     * 공지 삭제
     */
    @DeleteMapping("/noDelete")
    public ResponseEntity<?> noticeDelete(
            @RequestParam("num") int num
    ){


        noti.del(num);


        return ResponseEntity.ok(
                "공지 삭제 완료"
        );

    }



}