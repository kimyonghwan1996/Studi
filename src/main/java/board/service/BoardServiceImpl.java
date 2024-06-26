package board.service;

import board.bean.BoardDTO;
import board.bean.BoardPaging;
import board.bean.BoardReply;
import board.dao.BoardDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.service.ProjectService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BoardServiceImpl implements BoardService{

    @Autowired
    private BoardDAO boardDAO;
    @Autowired
    private ProjectService projectService;

    @Autowired
    private BoardPaging boardPaging;
    @Override
    public String boardInputData(BoardDTO boardDTO, String[] lang) {

         String lang_tmp="";

         for(int i=0;i<lang.length;i++){
             if(i==lang.length-1){
                 lang_tmp+=lang[i];
             }else{
                 lang_tmp+=lang[i]+",";
             }
         }
         boardDAO.boardInputData(boardDTO);
         Map<String,Object> map = new HashMap<>();
         map.put("boardId",boardDTO.getBOARDID());
         map.put("lang",lang_tmp);
         boardDAO.tagInput(map);

         /* PROJECT_MEMBER DB에 작성자 등록*/
        Map<String,String> projectMap = new HashMap<>();
        map.put("boardId", boardDTO.getBOARDID());
        map.put("userId", boardDTO.getUserId());
        projectService.approve(map);

        return "";
    }

    @Override
    public Map<String,Object> boardListGet(String pg) {

        //1페이지당 3개씩
        int startNum= (Integer.parseInt(pg)*5)-4;


        List<BoardDTO> list = boardDAO.boardListGet(startNum);
        //List -> JSON 변환


        //총글수 가져오기
        int total = boardDAO.getTotal();


        boardPaging.setCurrentPage(Integer.parseInt(pg));
        boardPaging.setPageBlock(3);
        boardPaging.setPageSize(5);
        boardPaging.setTotal(total);
        boardPaging.makePagingHTML();

        Map<String,Object> map = new HashMap<>();
        map.put("list", list);
        map.put("boardPaging", boardPaging.getPagingHTML().toString());

        return map;
    }

    @Override
    public String boardTagGet(String boardid) {
        return boardDAO.boardTagGet(boardid);
    }

    @Override
    public List<BoardDTO> boardListGet1(String type, String field) {
        Map<String,Object> map = new HashMap<>();
        if(type.length()==0){
            type="%p%";
        }
        if(field.length()==0){
            field="%e%";
        }

        map.put("projectType",type);
        map.put("projectField",field);


        return boardDAO.boardListGet1(map);
    }

    @Override
    public BoardDTO boardListGetbyId(String boardid) {
        return boardDAO.boardListGetbyId(boardid);
    }

    @Override
    public void addHit(String boardid) {
        boardDAO.addHit(boardid);
    }

    @Override
    public String boardScrap(String boardid, String userId) {
        Map<String, String> map = new HashMap<>();
        map.put("boardid",boardid);
        map.put("userId",userId);
        return boardDAO.boardScrap(map);
    }

    @Override
    public void addBoardScrap(String boardid, String userId) {
        Map<String, String> map = new HashMap<>();
        map.put("boardid",boardid);
        map.put("userId",userId);
        boardDAO.addBoardScrap(map);
    }

    @Override
    public String removeBoardScrap(String boardid, String userId) {
        Map<String, String> map = new HashMap<>();
        map.put("boardid",boardid);
        map.put("userId",userId);
        boardDAO.removeBoardScrap(map);
        return "";
    }

    @Override
    public String removeBoard(String boardid, String userId) {

        Map<String, String> map = new HashMap<>();
        map.put("boardid",boardid);
        map.put("userId",userId);
        boardDAO.removeBoardScrap(map);
        boardDAO.removeBoard(map);
        boardDAO.removeComment(map);
        boardDAO.removeTag(map);
        return "";
    }

    @Override
    public String addReply(String boardid, String userId, String text, int ref) {
        Map<String, Object> map = new HashMap<>();
        map.put("boardid",boardid);
        map.put("userId",userId);
        map.put("text",text);
        map.put("ref",ref);
        boardDAO.addReply(map);
        return "";
    }

    @Override
    public List<BoardReply> loadReply(String boardid) {
        return boardDAO.loadReply(boardid);
    }

    @Override
    public void boardEditData(BoardDTO boardDTO, String[] lang) {
        String lang_tmp="";

        for(int i=0;i<lang.length;i++){
            if(i==lang.length-1){
                lang_tmp+=lang[i];
            }else{
                lang_tmp+=lang[i]+",";
            }
        }
        boardDAO.boardEditData(boardDTO);
        Map<String,Object> map = new HashMap<>();
        map.put("boardId",boardDTO.getBOARDID());
        map.put("lang",lang_tmp);
        boardDAO.tagEdit(map);

    }

    @Override
    public List<BoardDTO> getHot5() {
        return boardDAO.getHot5();
    }

    @Override
    public List<BoardDTO> getRecent5() {
        return boardDAO.getRecent5();
    }

    @Override
    public void removeReply(String no) {
        boardDAO.removeReply(no);
    }

    @Override
    public void editReply(String no, String content) {
        Map<String,String> map = new HashMap<>();
        map.put("no",no);
        map.put("content",content);
        boardDAO.editReply(map);
    }

    @Override
    public void reportUser(String reportId, String reportText, String userid) {
        Map<String,String> map = new HashMap<>();
        map.put("reportId",reportId);
        map.put("reportText",reportText);
        map.put("userid",userid);
        boardDAO.reportUser(map);
    }

    @Override
    public List<BoardDTO> boardCntGetByUserId(String userId) {
        return boardDAO.boardCntGetByUserId(userId);
    }
}
