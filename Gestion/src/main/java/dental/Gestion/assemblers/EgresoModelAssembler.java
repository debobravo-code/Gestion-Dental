package dental.Gestion.assemblers;

import dental.Gestion.controller.EgresoControllerV2;
import dental.Gestion.model.Egreso;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class EgresoModelAssembler implements RepresentationModelAssembler<Egreso, EntityModel<Egreso>> {

    @Override
    public EntityModel<Egreso> toModel(Egreso egreso) {
        return EntityModel.of(egreso,
                linkTo(methodOn(EgresoControllerV2.class).buscarPorId(egreso.getIdEgreso())).withSelfRel(),
                linkTo(methodOn(EgresoControllerV2.class).listarEgresos()).withRel("egresos")
        );
    }
}