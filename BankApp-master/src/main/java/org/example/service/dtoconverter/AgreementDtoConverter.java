package org.example.service.dtoconverter;


import org.example.entity.Agreement;
import org.example.model.dto.AgreementDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgreementDtoConverter implements Converter<AgreementDto, Agreement> {

    @Autowired
    private ProductDtoConverter productDtoConverter;

    @Autowired
    private AccountDtoConverter accountDtoConverter;

    public AgreementDto toDto(Agreement agreement) {
        return new AgreementDto(accountDtoConverter.toDto(agreement.getAccount()),
                productDtoConverter.toDto(agreement.getProduct()), agreement.getInterestRate(),
                agreement.getStatus(), agreement.getSum());
    }

    public Agreement toEntity(AgreementDto agreementDto) {
        return new Agreement(agreementDto.getId(),
                agreementDto.getAccount() == null ?
                        accountDtoConverter.toEntity(agreementDto.getAccount()) : null,
                agreementDto.getProduct() == null ?
                        productDtoConverter.toEntity(agreementDto.getProduct()) : null,
                agreementDto.getInterestRate(),
                agreementDto.getStatus(), agreementDto.getSum());
    }
}