package net.mohamed.springmvc.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.mohamed.springmvc.entities.Product;
import net.mohamed.springmvc.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;

    @GetMapping({"/", "/index"})
    public String index(Model model,
                         @RequestParam(name = "page", defaultValue = "0") int page,
                         @RequestParam(name = "size", defaultValue = "5") int size,
                         @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        Page<Product> pages = productRepository.search("%" + keyword + "%", PageRequest.of(page, size));
        model.addAttribute("listProducts", pages.getContent());
        model.addAttribute("pages", pages);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "products";
    }

    @GetMapping("/deleteProduct")
    public String delete(@RequestParam("id") Long id) {
        productRepository.deleteById(id);
        return "redirect:/index";
    }

    @GetMapping("/newProduct")
    public String newProduct(Model model) {
        model.addAttribute("product", new Product());
        return "newProduct";
    }

    @GetMapping("/editProduct")
    public String editProduct(@RequestParam("id") Long id, Model model) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit introuvable : " + id));
        model.addAttribute("product", product);
        return "editProduct";
    }

    @PostMapping("/saveProduct")
    public String save(@Valid @ModelAttribute("product") Product product,
                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return product.getId() == null ? "newProduct" : "editProduct";
        }
        productRepository.save(product);
        return "redirect:/index";
    }
}
