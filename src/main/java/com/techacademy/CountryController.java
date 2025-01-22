package com.techacademy;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("country")
public class CountryController {
    private final CountryService service;

    public CountryController(CountryService service) {
        this.service = service;
    }

    // 一覧画面
    @GetMapping("/list")
    public String getList(Model model) {
        // 全件検索結果をModelに登録
        model.addAttribute("countrylist", service.getCountryList());
        // country/list.htmlに画面遷移
        return "country/list";
    }

    // 詳細画面
    @GetMapping(value = { "/detail", "/detail/{code}/" })
    public String getCountry(@PathVariable(name = "code", required = false) String code, Model model) {
        // codeが指定されていたら検索結果、なければ空のクラスを設定
        Country country = ( code != null ? service.getCountry(code) : new Country() );
        model.addAttribute("country", country);
        // country/detail.htmlに遷移
        return "country/detail";
    }

    // 更新(追加)
    @PostMapping("/detail")
    public String postCOuntry(@RequestParam("code") String code, @RequestParam("name") String name, @RequestParam("population") int population, Model model) {
        // 更新(追加)処理
        service.updateCountry(code, name, population);
        // 一覧画面にリダイレクト
        return "redirect:/country/list";
    }

    // 削除画面
    // 削除:課題 ここから
    // @GetMapping("/delete")
    // public String deleteCountryForm(Model model) {
    //    // country/delete.htmlに遷移
    //    return "country/delete";
    // }
    // 削除:課題 ここまで

    // 追加:課題 ここから
    @GetMapping(value = { "/delete", "/delete/{code}/" })
    public String deleteCountryForm(@PathVariable(name = "code", required = false) String code, Model model){
        // codeが指定されていたら検索結果、なければ空のクラスを設定(詳細画面と同様)
        Country country = ( code != null ? service.getCountry(code) : new Country() );
        model.addAttribute("country", country);
        // country/delete.htmlに遷移
        return "country/delete";
    }
    // 追加:課題 ここまで

    // 削除
    @PostMapping("/delete")
    public String deleteCountry(@RequestParam("code") String code, Model model) {
        // 削除
        service.deleteCoutnry(code);
        return "redirect:/country/list";
    }
}
