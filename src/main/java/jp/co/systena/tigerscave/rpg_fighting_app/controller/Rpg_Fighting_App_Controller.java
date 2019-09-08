package jp.co.systena.tigerscave.rpg_fighting_app.controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import jp.co.systena.tigerscave.rpg_fighting_app.model.Enemy;
import jp.co.systena.tigerscave.rpg_fighting_app.model.Fighter;
import jp.co.systena.tigerscave.rpg_fighting_app.model.Player;
import jp.co.systena.tigerscave.rpg_fighting_app.model.Profession;
import jp.co.systena.tigerscave.rpg_fighting_app.model.Warrior;
import jp.co.systena.tigerscave.rpg_fighting_app.model.Witch;

@Controller
public class Rpg_Fighting_App_Controller {

@Autowired
HttpSession session;

//キャラ格納用Mapデータ作成
Map<Integer, Profession> party = new HashMap<Integer, Profession>();

//敵キャラ作成
Enemy enemy = new Enemy();

//パーティ作成画面表示
@RequestMapping("/start")
public ModelAndView start(ModelAndView mav) {

  //debug
  //System.out.println("start_a");

  //  Player player = new Player();

//  mav.addObject("player", player);
  mav.setViewName("start");

  return mav;

}

//キャラ作成画面表示
@RequestMapping("/start_2")
public ModelAndView start_2(ModelAndView mav, @ModelAttribute("player") Player player) {

  //debug
  //System.out.println("start_2_b");
  //System.out.printf("%d\n",player.getName());
  //System.out.printf("%s\n",player.getProfession());
  //System.out.printf("%s\n",player.getName());
  //System.out.printf("%s\n",player.getMethod());
  //System.out.printf("%d\n",player.getNum());
  //System.out.printf("%d\n",player.getNo());

  // playerデータをセッションへ保存
  session.setAttribute("player", player);

  mav.setViewName("start_2");


return mav;

}

//コマンド入力画面表示
@GetMapping("/command")
public ModelAndView command(ModelAndView mav, @ModelAttribute("player") Player player,BindingResult bindingResult, HttpServletRequest request) {

  //エラーがある場合はそのまま戻す
  if (bindingResult.getAllErrors().size() > 0) {
    //debug
    //System.out.println("command_c");

    mav.addObject("player", player);
    mav.setViewName("start");

    return mav;
  }

  //debug
  //System.out.println("command_d");
  //System.out.printf("%s\n",player.getProfession());
  //System.out.printf("%s\n",player.getName());
  //System.out.printf("%s\n",player.getMethod());
  //System.out.printf("%d\n",player.getNum());
  //System.out.printf("%d\n",player.getNo());

  //Playerデータをセッションから取り出し
  Player player_session = (Player)session.getAttribute("player");

  //debug
  //System.out.println("command_d_2\n");
  //System.out.printf("%s\n",player_session.getProfession());
  //System.out.printf("%s\n",player_session.getName());
  //System.out.printf("%s\n",player_session.getMethod());
  //System.out.printf("%d\n",player_session.getNum());
  //System.out.printf("%d\n",player_session.getNo());

  //パーティ人数、キャラ作成済人数を設定
  player.setNum(player_session.getNum());
  player.setNo(player_session.getNo()+1);

  // playerデータをセッションへ保存
  session.setAttribute("player", player);


  //選択された職業からインスタンス生成
  switch (player.getProfession()) {
  case "戦士":
    //debug
    //System.out.printf("warrior\n");
    // 戦士クラスをインスタンス化
    Profession profession_warrior = new Warrior();
    profession_warrior.setName(player.getName());
    profession_warrior.setProfession(player.getProfession());

    // Mapへ保存
    party.put(player.getNo(), profession_warrior);

    // Mapデータをセッションへ保存
    session.setAttribute("profession", party);
    break;

  case "魔法使い":
    //debug
    //System.out.printf("witch\n");
    // 魔法使いクラスをインスタンス化
    Profession profession_witch = new Witch();
    profession_witch.setName(player.getName());
    profession_witch.setProfession(player.getProfession());

    // Mapへ保存
    party.put(player.getNo(), profession_witch);

    // Mapデータをセッションへ保存
    session.setAttribute("profession", party);
    break;

  case "武闘家":
    //debug
    //System.out.printf("fighter\n");
    // 武闘家クラスをインスタンス化
    Profession profession_fighter = new Fighter();
    profession_fighter.setName(player.getName());
    profession_fighter.setProfession(player.getProfession());

    // Mapへ保存
    party.put(player.getNo(), profession_fighter);

    // Mapデータをセッションへ保存
    session.setAttribute("profession", party);
    break;

  case "":
    break;
  default:
    break;
  }

  mav.setViewName("command");
  return mav;

}

//コマンド入力画面表示(再入力時用)
@GetMapping("/command_2")
public ModelAndView command_2(ModelAndView mav, @ModelAttribute("player") Player player) {

  //System.out.printf("command_2_a\n");
  //セッションからMAPデータを取り出してplayerにキャラデータを設定してコマンド画面を表示する
  //Mapに既に格納されている1～4個のデータを取り出して1個ずつ設定する。
  //既存のコマンド画面はキャラ作成→コマンドがセットになっているので新たにコマンド実行のみを行うメソッドを作成する。
  //Mapデータを格納分1つずつ取り出す方法?
  //playerにキャラデータを設定してコマンド画面を表示する

  //Mapデータをセッションから取り出し
  Map<Integer, Profession> party = (Map<Integer, Profession>)session.getAttribute("profession");

  //MapデータのProgessionデータをNoが最後からPlayerデータに設定
  Player player_session = (Player)session.getAttribute("player");
  //Profession profession = party.get(player_session.getNo());
  Profession profession = party.get(player.getNo());

  //MapデータのProgessionデータをNoが最後からPlayerデータに設定
  player_session.setProfession(profession.getProfession());
  player_session.setName(profession.getName());
  player_session.setMethod(null);
  player_session.setNo(player.getNo());

  //debug
  //System.out.println("player");
  //System.out.printf("%s\n",player.getProfession());
  //System.out.printf("%s\n",player.getName());
  //System.out.printf("%s\n",player.getMethod());
  //System.out.printf("%d\n",player.getNum());
  //System.out.printf("%d\n",player.getNo());

  //System.out.println("player_session");
  //System.out.printf("%s\n",player_session.getProfession());
  //System.out.printf("%s\n",player_session.getName());
  //System.out.printf("%s\n",player_session.getMethod());
  //System.out.printf("%d\n",player_session.getNum());
  //System.out.printf("%d\n",player_session.getNo());

  // playerデータをセッションへ保存
  session.setAttribute("player", player_session);

  mav.addObject("player", player_session);
  mav.setViewName("command_2");
  return mav;

}

//コマンド実行結果画面表示
//@GetMapping("/end")
@RequestMapping(value = "/end")
//public ModelAndView end(ModelAndView mav, @ModelAttribute("profession") Profession profession,BindingResult bindingResult, HttpServletRequest request) {
public ModelAndView end_1(ModelAndView mav,@ModelAttribute("player") Player player,BindingResult bindingResult, HttpServletRequest request) {

  //debug
  //System.out.println("end_1");
  //System.out.printf("%s\n",player.getProfession());
  //System.out.printf("%s\n",player.getName());
  //System.out.printf("%s\n",player.getMethod());
  //System.out.printf("%d\n",player.getNum());
  //System.out.printf("%d\n",player.getNo());

  //Playerデータをセッションから取り出し
  Player player_session = (Player)session.getAttribute("player");

  //debug
  //System.out.println("player_session");
  //System.out.printf("%s\n",player_session.getProfession());
  //System.out.printf("%s\n",player_session.getName());
  //System.out.printf("%s\n",player_session.getMethod());
  //System.out.printf("%d\n",player_session.getNum());
  //System.out.printf("%d\n",player_session.getNo());

  //コマンドを設定
  player_session.setMethod(player.getMethod());

  //debug
  //System.out.println("player_session_2");
  //System.out.printf("%s\n",player_session.getProfession());
  //System.out.printf("%s\n",player_session.getMethod());
  //System.out.printf("%s\n",player_session.getName());
  //System.out.printf("%d\n",player_session.getNum());
  //System.out.printf("%d\n",player_session.getNo());

  // playerデータをセッションへ保存
  session.setAttribute("player", player_session);


  //セッションから取り出し
  Map<Integer, Profession> party = (Map<Integer, Profession>)session.getAttribute("profession");
  Profession profession = party.get(player_session.getNo());

  //debug
  //System.out.println("profession");
  //System.out.printf("%s\n",profession.getProfession());
  //System.out.printf("%s\n",profession.getName());
  //System.out.printf("%s\n",profession.getMethod());


  //コマンド画面で実行したコマンド種別を保存する(たたかう/かいふく)
  switch (player_session.getMethod()) {
  case "fight":
    //debug
    //System.out.printf("fight");
    profession.Fight();

    //敵HP更新
    if (enemy.getHp() != 0) {
      enemy.setHp(enemy.getHp()-10);
    }

    break;
  case "recover":
    //debug
    //System.out.printf("recover");
    profession.Recover();
    break;
  default:
    break;
  }

  // Mapへ再保存
  party.put(player_session.getNo(), profession);

  // Mapデータをセッションへ保存
  session.setAttribute("profession", party);

  //debug
  //System.out.printf("%d\n",player_session.getNum());
  //System.out.printf("%d\n",party.size());
  //System.out.printf("%d\n",player_session.getNo());

  //Mapに格納済のデータ数がパーティ人数未満であれば再度キャラ作成画面へ遷移
  if(party.size() < player_session.getNum()) {
    //debug
    //System.out.println("f");

    //player_session.setNo(party.size());         //作成済キャラ人数設定
    mav.addObject("player", player_session);
    mav.setViewName("start_2");

    return mav;

  }

  //コマンド入力済キャラ数がパーティ人数未満であれば再度コマンド入力画面へ遷移
  if(player_session.getNo() < player_session.getNum()) {
    //debug
    //System.out.println("g");

    //Mapから次のキャラのデータを取り出し
    profession = party.get(player_session.getNo()+1);
    //次のキャラのデータをPlayerにセットする
    //player_session.setProfession(profession.getProfession());
    player_session.setProfession(profession.getProfession());
    player_session.setName(profession.getName());
    player_session.setNo(player_session.getNo()+1);         //コマンド入力済キャラ人数設定
    // playerデータをセッションへ保存
    session.setAttribute("player", player_session);

    mav.addObject("player", player_session);
    mav.setViewName("command_2");

    return mav;

  }

  mav.addObject("party", party);
  mav.addObject("enemy", enemy);
  mav.setViewName("end");
  return mav;

}

}
