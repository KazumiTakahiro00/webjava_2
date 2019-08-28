package jp.co.systena.tigerscave.rpg_fighting_app.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import jp.co.systena.tigerscave.rpg_fighting_app.model.Fighter;
import jp.co.systena.tigerscave.rpg_fighting_app.model.Player;
import jp.co.systena.tigerscave.rpg_fighting_app.model.Profession;
import jp.co.systena.tigerscave.rpg_fighting_app.model.Warrior;
import jp.co.systena.tigerscave.rpg_fighting_app.model.Witch;

@Controller
public class Rpg_Fighting_App_Controller {

@Autowired
HttpSession session;

//キャラ作成画面表示
@RequestMapping("/start")
public ModelAndView start(ModelAndView mav) {

//  Player player = new Player();

//  mav.addObject("player", player);
  mav.setViewName("start");

  //debug
  System.out.println("a");
//  System.out.printf("%d\n",player.getName());

  return mav;

}

//コマンド画面表示
@GetMapping("/command")
public ModelAndView command(ModelAndView mav, @ModelAttribute("player") Player player,BindingResult bindingResult, HttpServletRequest request) {

  //エラーがある場合はそのまま戻す
  if (bindingResult.getAllErrors().size() > 0) {
    //値が設定されているかコンソールに表示してみる(debug)
    System.out.println("c");

    mav.addObject("player", player);
    mav.setViewName("start");

    return mav;
  }

  //debug
  System.out.println("b");
  System.out.printf("%s\n",player.getProfession());
  System.out.printf("%s\n",player.getName());

  //選択された職業からインスタンス生成
  switch (player.getProfession()) {
  case "戦士":
    System.out.printf("warrior");
    // 戦士クラスをインスタンス化
    Profession profession_warrior = new Warrior();
    profession_warrior.setName(player.getName());
    profession_warrior.setProfession(player.getProfession());

    // データをセッションへ保存
    session.setAttribute("profession", profession_warrior);
    break;

  case "魔法使い":
    System.out.printf("witch");
    // 魔法使いクラスをインスタンス化
    Profession profession_witch = new Witch();
    profession_witch.setName(player.getName());
    profession_witch.setProfession(player.getProfession());

    // データをセッションへ保存
    session.setAttribute("profession", profession_witch);
    break;

  case "武闘家":
    System.out.printf("fighter");
    // 武闘家クラスをインスタンス化
    Profession profession_fighter = new Fighter();
    profession_fighter.setName(player.getName());
    profession_fighter.setProfession(player.getProfession());

    // データをセッションへ保存
    session.setAttribute("profession", profession_fighter);
    break;

  case "":
    break;
  default:
    break;
  }


  mav.setViewName("command");
  return mav;

}

//コマンド画面表示
@GetMapping("/end")
//public ModelAndView end(ModelAndView mav, @ModelAttribute("profession") Profession profession,BindingResult bindingResult, HttpServletRequest request) {
public ModelAndView end(ModelAndView mav) {

  System.out.println("e");

  //セッションから取り出しfightメソッド実行し名称を設定
  Profession profession = (Profession)session.getAttribute("profession");

  profession.Fight();
  System.out.printf("%s\n",profession.getMethod());

  mav.addObject("profession", profession);
  mav.setViewName("end");
  return mav;

}


}
