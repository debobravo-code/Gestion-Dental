package dental.Gestion.controller;

import dental.Gestion.assemblers.EgresoModelAssembler;
import dental.Gestion.model.Egreso;
import dental.Gestion.service.EgresoService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/egresos")
@RequiredArgsConstructor
public class EgresoControllerV2 {

    private final EgresoService egresoService;
    private final EgresoModelAssembler assembler;

    @GetMapping
    public CollectionModel<EntityModel<Egreso>> listarEgresos() {
        List<EntityModel<Egreso>> egresos = egresoService.listarEgresos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(egresos,
                linkTo(methodOn(EgresoControllerV2.class).listarEgresos()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Egreso> buscarPorId(@PathVariable Integer id) {
        Egreso egreso = egresoService.buscarPorId(id);
        return assembler.toModel(egreso);
    }

    @GetMapping("/insumo/{insumoId}")
    public CollectionModel<EntityModel<Egreso>> buscarPorInsumoId(@PathVariable Integer insumoId) {
        List<EntityModel<Egreso>> egresos = egresoService.buscarPorInsumoId(insumoId).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(egresos,
                linkTo(methodOn(EgresoControllerV2.class).buscarPorInsumoId(insumoId)).withSelfRel(),
                linkTo(methodOn(EgresoControllerV2.class).listarEgresos()).withRel("egresos"));
    }

    @GetMapping("/destino/{destinoId}")
    public CollectionModel<EntityModel<Egreso>> buscarPorDestinoId(@PathVariable Integer destinoId) {
        List<EntityModel<Egreso>> egresos = egresoService.buscarPorDestinoId(destinoId).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(egresos,
                linkTo(methodOn(EgresoControllerV2.class).buscarPorDestinoId(destinoId)).withSelfRel(),
                linkTo(methodOn(EgresoControllerV2.class).listarEgresos()).withRel("egresos"));
    }

    @GetMapping("/fecha/{fecha}")
    public CollectionModel<EntityModel<Egreso>> buscarPorFecha(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {

        List<EntityModel<Egreso>> egresos = egresoService.buscarPorFecha(fecha).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(egresos,
                linkTo(methodOn(EgresoControllerV2.class).buscarPorFecha(fecha)).withSelfRel(),
                linkTo(methodOn(EgresoControllerV2.class).listarEgresos()).withRel("egresos"));
    }
    @GetMapping("/fechas")
    public CollectionModel<EntityModel<Egreso>> buscarPorRangoFechas(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {

        List<EntityModel<Egreso>> egresos = egresoService.buscarPorRangoFechas(inicio, fin).stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(egresos,
            linkTo(methodOn(EgresoControllerV2.class).buscarPorRangoFechas(inicio, fin)).withSelfRel(),
            linkTo(methodOn(EgresoControllerV2.class).listarEgresos()).withRel("egresos"));
    }
    

}
