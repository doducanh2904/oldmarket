package com.due.oldmarket.controller;

import com.due.oldmarket.dto.ProductDTO;
import com.due.oldmarket.exception.ErrorResponse;
import com.due.oldmarket.exception.FileStorageException;
import com.due.oldmarket.exception.ProductAlreadyExistsException;
import com.due.oldmarket.model.File;
import com.due.oldmarket.model.Product;
import com.due.oldmarket.payload.UploadFileResponse;
import com.due.oldmarket.service.FileStorageService;
import com.due.oldmarket.service.ProductService;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Các Method liên quan đến Model Product
 *
 * @author Do Duc Anh
 * @since 2022-10-23
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @Autowired
    FileStorageService fileStorageService;


    /**
     * Method này insert 1 row product
     *
     * @param : product
     * @return String information
     * @api : http://localhost:8080/product/insert/{idUser}/{idCategory}
     * @method : POST
     */

    @PostMapping(value = "insert")
    public ResponseEntity<String> insert(@PathVariable(value = "idUser") Long id,
                                         @PathVariable(value = "idCategory") Long idCategory,
                                         @RequestBody Product product,
                                         BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            for (final FieldError error : result.getFieldErrors()) {
                System.out.println(errors.append("/" + error.getDefaultMessage()));
            }
        }
        product.setStatus("pending");
        productService.save(id, idCategory, product);
        return new ResponseEntity<>("Product add successfully", HttpStatus.OK);
    }

    //    Example Url : http://localhost:8080/product/insertProductAndFile?idUser=3&idCategory=1


    /**
     * Method này insert 1 row product và 1 row file
     *
     * @param : product
     * @param : idUer
     * @param : idCategory
     * @param : MultipartFile file1
     * @return UploadFileResponse
     * @api : http://localhost:8080/product/insertProductAndFile?idUser=...&idCategory=...
     * @method : POST
     */
    @PostMapping(value = "/insertProductAndFile")
    public UploadFileResponse insertProductAndFile(@ModelAttribute Product product, BindingResult result,
                                                   @RequestParam(name = "idUser") Long idUer,
                                                   @RequestParam(name = "idCategory") Long idCategory,
                                                   @RequestParam("file1") MultipartFile file1) {
        if (result.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            for (final FieldError error : result.getFieldErrors()) {
                System.out.println(errors.append("/" + error.getDefaultMessage()));
            }
        }
        try {
            product.setStatus("pending");
            productService.save(idUer, idCategory, product);
        } catch (Exception e) {
            throw new FileStorageException("Đã xảy ra lỗi ", e);
        }
        /*Long a = productService.findById(product.getIdProduct()).getIdProduct();*/
        if (file1.isEmpty()) {
            return new UploadFileResponse("Add Product successfully and no image yet");
        }
        File dbFile = fileStorageService.storeFileProduct(file1, product.getIdProduct());
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("file/downloadFile/")
                .path(dbFile.getId())
                .toUriString();

        return new UploadFileResponse(dbFile.getId(), dbFile.getFileName(), fileDownloadUri,
                file1.getContentType(), file1.getSize());
    }


    /**
     * Method này insert 1 row product và nhiều row file
     *
     * @param : product
     * @param : idUer
     * @param : idCategory
     * @param : MultipartFile file1
     * @return List<UploadFileResponse>
     * @api : http://localhost:8080/product/insertProductAndMulFile?idUser=...&idCategory=...}
     * @method : POST
     */


    @PostMapping(value = "/insertProductAndMulFile")
    public List<UploadFileResponse> inserProductAndMulFile(@ModelAttribute Product product,
                                                           BindingResult result,
                                                           @RequestParam(name = "idUser") Long idUer,
                                                           @RequestParam(name = "idCategory") Long idCategory,
                                                           @RequestParam("files") MultipartFile[] files) {

        List<UploadFileResponse> repo = new ArrayList<UploadFileResponse>();

        for (MultipartFile file : files) {
            repo.add(insertProductAndFile(product, result, idUer, idCategory, file));
        }
        return repo;
    }

    /**
     * Method này lấy ra  1 row product theo điều kiện id truyền vào
     *
     * @param : id
     * @return productDTO
     * @api : http://localhost:8080/product/selectById/{id}}
     * @method : GET
     */

        @GetMapping(value = "/selectById/{id}")
    public ResponseEntity<ProductDTO> selectById(@PathVariable(value = "id") Long id) {
        Product p = productService.findById(id);

        ProductDTO productDTO = new ProductDTO();
        if (p.getStatus().equals("active")) {
            productDTO.setIdProduct(p.getIdProduct());
            productDTO.setProductName(p.getProductName());
            productDTO.setPrice(p.getPrice());
            productDTO.setDescription(p.getDescription());
            productDTO.setTradePark(p.getTradePark());
            productDTO.setAmount(p.getAmount());
            productDTO.setIdUser(p.getUser().getIdUser());
            productDTO.setIdCategory(p.getCategory().getIdCategory());
            productDTO.setCategory(p.getCategory());
            List<String> idFileList = new ArrayList<>();
            for (File file : p.getFile()) {

                String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("file/downloadFile/")
                        .path(file.getId())
                        .toUriString();
                idFileList.add(fileDownloadUri);
            }
            productDTO.setUrlFile(idFileList);
        }

        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    /**
     * Method này lấy ra  1 List product theo điều kiện List Id truyền vào
     *
     * @param : Lit id
     * @return productDTOList
     * @api : http://localhost:8080/product/selectByMulId?mulId=1,2,3
     * @method : GET
     */

    @GetMapping(value = "/selectByMulId")
    public ResponseEntity<List<ProductDTO>> selectByMulId(@RequestParam(value = "mulId") List<Long> mulIdProduct) {
        ProductDTO productDTO;
        List<ProductDTO> productDTOList = new ArrayList<>();
        for (Long id : mulIdProduct) {
            Product p = productService.findById(id);
            if (p.getStatus().equals("deleted")) {
                continue;
            } else {
                productDTO = new ProductDTO();

                productDTO.setIdProduct(p.getIdProduct());
                productDTO.setProductName(p.getProductName());
                productDTO.setPrice(p.getPrice());
                productDTO.setDescription(p.getDescription());
                productDTO.setTradePark(p.getTradePark());
                productDTO.setAmount(p.getAmount());
                productDTO.setIdUser(p.getUser().getIdUser());
                productDTO.setIdCategory(p.getCategory().getIdCategory());
                productDTO.setCategory(p.getCategory());

                List<String> idFileList = new ArrayList<>();
                for (File file : p.getFile()) {

                    String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path("file/downloadFile/")
                            .path(file.getId())
                            .toUriString();
                    idFileList.add(fileDownloadUri);
                }
                productDTO.setUrlFile(idFileList);

                productDTOList.add(productDTO);
            }
        }
        if (productDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productDTOList, HttpStatus.OK);
    }


    /**
     * Method này lấy ra  toàn bộ row product
     *
     * @return List<ProductDTO>
     * @api : http://localhost:8080/product/selectAll}
     * @method : GET
     */
    @CrossOrigin
    @GetMapping(value = "/selectAll")
    public ResponseEntity<List<ProductDTO>> selectAll() {
        List<Product> productList = productService.findAll();
        ProductDTO productDTO;
        List<ProductDTO> productDTOList = new ArrayList<>();
        for (Product p : productList) {
            if (p.getStatus().equals("deleted")) {
                continue;
            } else {
                productDTO = new ProductDTO();
                productDTO.setIdProduct(p.getIdProduct());
                productDTO.setProductName(p.getProductName());
                productDTO.setPrice(p.getPrice());
                productDTO.setDescription(p.getDescription());
                productDTO.setTradePark(p.getTradePark());
                productDTO.setAmount(p.getAmount());
                productDTO.setStatus(p.getStatus());
                productDTO.setIdUser(p.getUser().getIdUser());
                productDTO.setIdCategory(p.getCategory().getIdCategory());
                productDTO.setCategory(p.getCategory());
                List<String> idFileList = new ArrayList<>();
                for (File file : p.getFile()) {

                    String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path("file/downloadFile/")
                            .path(file.getId())
                            .toUriString();
                    idFileList.add(fileDownloadUri);
                }
                productDTO.setUrlFile(idFileList);

                productDTOList.add(productDTO);
            }
        }
        if (productDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productDTOList, HttpStatus.OK);
    }


    @GetMapping(value = "/selectAllPaging")
    public ResponseEntity<List<ProductDTO>> selectAllPaging(@RequestParam(value = "pageNo") int pageNo,
                                                            @RequestParam(value = "pageSize") int pageSize,
                                                            @RequestParam(value = "status")String status) {
        List<Product> productList = productService.findAll(pageNo, pageSize);
        ProductDTO productDTO;
        List<ProductDTO> productDTOList = new ArrayList<>();
        for (Product p : productList) {
            if (p.getStatus().equals(status)) {

                productDTO = new ProductDTO();
                productDTO.setIdProduct(p.getIdProduct());
                productDTO.setProductName(p.getProductName());
                productDTO.setStatus(p.getStatus());
                productDTO.setPrice(p.getPrice());
                productDTO.setDescription(p.getDescription());
                productDTO.setTradePark(p.getTradePark());
                productDTO.setAmount(p.getAmount());
                productDTO.setIdUser(p.getUser().getIdUser());
                productDTO.setIdCategory(p.getCategory().getIdCategory());

                List<String> idFileList = new ArrayList<>();
                for (File file : p.getFile()) {

                    String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path("file/downloadFile/")
                            .path(file.getId())
                            .toUriString();
                    idFileList.add(fileDownloadUri);
                }
                productDTO.setUrlFile(idFileList);

                productDTOList.add(productDTO);

            }
        }
        if (productDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productDTOList, HttpStatus.OK);
    }

    /**
     * Method này chỉnh sửa thông tin product theo id và dữ liệu truyền vào
     *
     * @return Product
     * @api : http://localhost:8080/product/update/{id}
     * @method : PUT
     */

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Product> update(@PathVariable(value = "id") Long id, @RequestBody Product product) {
        productService.update(id, product);
        return new ResponseEntity<>(productService.findById(id), HttpStatus.OK);
    }

    /**
     * Method này chỉnh sửa thành product đã xóa theo id và dữ liệu truyền vào
     *
     * @param : List<Long> id
     * @api : http://localhost:8080/deleteListProduct/1,2,...
     * @method : PUT
     */

    @PutMapping(value = "/deleteListProduct/{listProductId}")
    public ResponseEntity<String> deleteListProduct(@PathVariable(value = "listProductId") List<Long> id,
                                                    @RequestParam (value = "status") String status) {
        productService.deteleList(id,status);
        if(status.equals("deleted")) {
            return new ResponseEntity<>("Delete Successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Accept Successfully", HttpStatus.OK);
    }


    @GetMapping(value = "/selectByNameProductPaging")
    public ResponseEntity<List<ProductDTO>> selectByNameProductPaging(@RequestParam(value = "param") String param,
                                                                      @RequestParam(value = "pageNo") int pageNo,
                                                                      @RequestParam(value = "pageSize") int pageSize,
                                                                      @RequestParam(value = "status")String status) {
        List<Product> productList = productService.findByName(param, pageNo, pageSize);
        ProductDTO productDTO;
        List<ProductDTO> productDTOList = new ArrayList<>();
        for (Product p : productList) {
            if (p.getStatus().equals(status)) {
                productDTO = new ProductDTO();
                productDTO.setIdProduct(p.getIdProduct());
                productDTO.setProductName(p.getProductName());
                productDTO.setPrice(p.getPrice());
                productDTO.setStatus(p.getStatus());
                productDTO.setDescription(p.getDescription());
                productDTO.setTradePark(p.getTradePark());
                productDTO.setAmount(p.getAmount());
                productDTO.setIdUser(p.getUser().getIdUser());
                productDTO.setIdCategory(p.getCategory().getIdCategory());
                productDTO.setCategory(p.getCategory());

                List<String> idFileList = new ArrayList<>();
                for (File file : p.getFile()) {

                    String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path("file/downloadFile/")
                            .path(file.getId())
                            .toUriString();
                    idFileList.add(fileDownloadUri);
                }
                productDTO.setUrlFile(idFileList);

                productDTOList.add(productDTO);
            }
        }
        if (productDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productDTOList, HttpStatus.OK);
    }

    @GetMapping(value = "/selectByParamProductPaging")
    public ResponseEntity<List<ProductDTO>> selectByParamProductPaging(@RequestParam(value = "param") String param,
                                                                       @RequestParam(value = "pageNo") int pageNo,
                                                                       @RequestParam(value = "pageSize") int pageSize,
                                                                       @RequestParam(value = "status")String status) {
        List<Product> productList = productService.findByParamProduct(param, pageNo, pageSize);
        ProductDTO productDTO;
        List<ProductDTO> productDTOList = new ArrayList<>();
        for (Product p : productList) {
            if (p.getStatus().equals(status)) {

                productDTO = new ProductDTO();
                productDTO.setIdProduct(p.getIdProduct());
                productDTO.setProductName(p.getProductName());
                productDTO.setPrice(p.getPrice());
                productDTO.setStatus(p.getStatus());
                productDTO.setDescription(p.getDescription());
                productDTO.setTradePark(p.getTradePark());
                productDTO.setAmount(p.getAmount());
                productDTO.setIdUser(p.getUser().getIdUser());
                productDTO.setIdCategory(p.getCategory().getIdCategory());
                productDTO.setCategory(p.getCategory());

                List<String> idFileList = new ArrayList<>();
                for (File file : p.getFile()) {

                    String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path("file/downloadFile/")
                            .path(file.getId())
                            .toUriString();
                    idFileList.add(fileDownloadUri);
                }
                productDTO.setUrlFile(idFileList);

                productDTOList.add(productDTO);
            }
        }
        if (productDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productDTOList, HttpStatus.OK);
    }


    @GetMapping(value = "/selectByIdCategoryPaging/{idCategory}")
    public ResponseEntity<List<ProductDTO>> selectByIdCategoryPaging(@PathVariable(value = "idCategory") Long idCategory,
                                                                     @RequestParam(value = "pageNo") int pageNo,
                                                                     @RequestParam(value = "pageSize") int pageSize,
                                                                     @RequestParam(value = "status")String status) {
        List<Product> productList = productService.findByIdCategory(idCategory, pageNo, pageSize);
        ProductDTO productDTO;
        List<ProductDTO> productDTOList = new ArrayList<>();
        for (Product p : productList) {
            if (p.getStatus().equals(status)) {

                productDTO = new ProductDTO();
                productDTO.setIdProduct(p.getIdProduct());
                productDTO.setProductName(p.getProductName());
                productDTO.setPrice(p.getPrice());
                productDTO.setStatus(p.getStatus());
                productDTO.setDescription(p.getDescription());
                productDTO.setTradePark(p.getTradePark());
                productDTO.setAmount(p.getAmount());
                productDTO.setIdUser(p.getUser().getIdUser());
                productDTO.setIdCategory(p.getCategory().getIdCategory());
                productDTO.setCategory(p.getCategory());

                List<String> idFileList = new ArrayList<>();
                for (File file : p.getFile()) {

                    String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path("file/downloadFile/")
                            .path(file.getId())
                            .toUriString();
                    idFileList.add(fileDownloadUri);
                }
                productDTO.setUrlFile(idFileList);

                productDTOList.add(productDTO);
            }
        }
        if (productDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productDTOList, HttpStatus.OK);
    }





/**
 * Method này lấy ra những sản phẩm mà người bán đã đăng bán theo status
 * @param status (pending, active, deleled)
 * @return Set<Product>
 * @api : http://localhost:8080/product/findByIdUserSalesman/{idUser}?status=
 * @method : GET
 */

    @GetMapping(value = "/findByIdUserSalesman/{idUser}")
    public ResponseEntity<Set<ProductDTO>> findByIdUserSalesman(@PathVariable(value = "idUser") Long idUser,
                                                                @RequestParam(value = "status") String status) {
        Set<Product> productSet = productService.findByIdUserSalesman(idUser, status);
        ProductDTO productDTO;
        Set<ProductDTO> productDTOSet = new HashSet<>();
        for (Product p : productSet) {
            productDTO = new ProductDTO();
            productDTO.setIdProduct(p.getIdProduct());
            productDTO.setProductName(p.getProductName());
            productDTO.setPrice(p.getPrice());
            productDTO.setDescription(p.getDescription());
            productDTO.setTradePark(p.getTradePark());
            productDTO.setAmount(p.getAmount());
            productDTO.setStatus(p.getStatus());
            productDTO.setIdUser(p.getUser().getIdUser());
            productDTO.setIdCategory(p.getCategory().getIdCategory());
            productDTO.setCategory(p.getCategory());

            List<String> idFileList = new ArrayList<>();
            for (File file : p.getFile()) {

                String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("file/downloadFile/")
                        .path(file.getId())
                        .toUriString();
                idFileList.add(fileDownloadUri);
            }
            productDTO.setUrlFile(idFileList);

            productDTOSet.add(productDTO);

        }
        if (productDTOSet.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productDTOSet, HttpStatus.OK);
    }

}

