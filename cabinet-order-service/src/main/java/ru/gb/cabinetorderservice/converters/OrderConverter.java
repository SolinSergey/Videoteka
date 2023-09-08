package ru.gb.cabinetorderservice.converters;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Component;
import ru.gb.api.dtos.dto.FilmDto;
import ru.gb.api.dtos.dto.OrderDto;
import ru.gb.cabinetorderservice.entities.Order;
import ru.gb.cabinetorderservice.integrations.FilmServiceIntegration;

@Component
@RequiredArgsConstructor
public class OrderConverter {
    private final FilmServiceIntegration filmServiceIntegration;


    public Order dtoToEntity(SpringDataJaxb.OrderDto orderDto) {
        throw new UnsupportedOperationException();
    }

    public OrderDto entityToDto(Order order) {
        OrderDto out = new OrderDto();
        out.setId(order.getId());
        out.setUserId(order.getUserId());
        out.setFilmId(order.getFilmId());
        out.setSalePrice(order.getSalePrice());
        out.setRentPrice(order.getRentPrice());
        out.setSale(order.getType().equals("SALE"));
        Long filmId = order.getFilmId();
        FilmDto filmDto = filmServiceIntegration.findById(filmId);
        out.setFilmTitle(filmDto.getTitle());
        out.setDescription(filmDto.getDescription());
        out.setImageUrlLink(filmDto.getImageUrlLink());
        out.setRentStart(order.getRentStart());
        out.setRentEnd(order.getRentEnd());
        return out;
    }
}