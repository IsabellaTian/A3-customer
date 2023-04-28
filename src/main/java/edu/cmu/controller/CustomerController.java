package edu.cmu.controller;

import edu.cmu.model.Customer;
import edu.cmu.response.ErrorMessage;
import edu.cmu.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/customers")
public class CustomerController {

    final
    CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
        customerService.deleteAll();
    }

    @Operation(summary = "Add a new customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully added a new customer",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Customer.class)) }),
            @ApiResponse(responseCode = "400", description = "Illegal, missing, or malformed input", content = @Content),
            @ApiResponse(responseCode = "422", description = "User-ID already exists in the system", content = @Content)
    })
    @PostMapping("")
    public ResponseEntity<?> addCustomer(@Valid @RequestBody Customer customer) {
        if (customer.isStateInvalid()) {
            return new ResponseEntity<>(new ErrorMessage("The state of the customer is not valid"), HttpStatus.BAD_REQUEST);
        }
        return customerService.addCustomer(customer);
    }

    @Operation(summary = "retrieve a customer by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the customer",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Customer.class)) }),
            @ApiResponse(responseCode = "404", description = "Customer Not Found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Illegal, missing, or malformed input", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> retrieveCustomerById(@PathVariable int id) {
        var customer = customerService.findById(id);
        return customer == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @Operation(summary = "retrieve a customer by userId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the customer",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Customer.class)) }),
            @ApiResponse(responseCode = "404", description = "Customer Not Found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Illegal, missing, or malformed input", content = @Content)
    })
    @GetMapping("")
    public ResponseEntity<?> retrieveCustomerByUserId(@RequestParam @Email String userId) {
        var customer = customerService.findByUserId(userId);
        return customer == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResponseEntity<?> validationError(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
