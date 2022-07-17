package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    private String createForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    private String create(BookForm form) {

        Book book = new Book();

        // TODO: static 생성 메서드
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setIsbn(form.getIsbn());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());

        itemService.saveItem(book);

        // 저장 후 저장된 아이템 목록으로 리다이렉트
        return "redirect:/";
    }

    @GetMapping("/items")
    private String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Book item = (Book) itemService.findOne(itemId); // 예제가 간단하므로 Book으로 캐스팅

        BookForm form = new BookForm(); // 수정을 위해 폼 엔티티를 보냄
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());

        model.addAttribute("form", form);
        return "items/updateItemForm";
    }

    @PostMapping("/items/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId, @ModelAttribute("form") BookForm form) {



        // 필요한 데이터만 받아서 수정하는 것이 더 나은 설계
        itemService.updateItem(itemId,
                form.getName(), form.getPrice(), form.getStockQuantity());

        /**
         * 준영속 엔티티
         * - 수정을 위해 만드는 {@code Book} 엔티티는 준영속 엔티티임
         * - 임의로 만들어진 엔티티이지만, 영속된 적이 있고, JPA가 관리하는 식별자가 있음
         * - 영속성 컨텍스트가 더 이상 관리하지 않음
         * - 참고: {@code ItemUpdateTest.java}
         */

        // FIX: 좋은 설계가 아님

//        Book book = new Book();
//
//        // TODO: 아이템 수정 권한 있는지 체크
//        book.setId(form.getId());
//        book.setName(form.getName());
//        book.setPrice(form.getPrice());
//        book.setStockQuantity(form.getStockQuantity());
//        book.setAuthor(form.getAuthor());
//        book.setIsbn(form.getIsbn());
//
//        itemService.saveItem(book);
        return "redirect:/items";
    }
}

