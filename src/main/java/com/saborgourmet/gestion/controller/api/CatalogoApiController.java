package com.saborgourmet.gestion.controller.api;

import com.saborgourmet.gestion.model.enums.EstadoFactura;
import com.saborgourmet.gestion.model.enums.EstadoMesa;
import com.saborgourmet.gestion.model.enums.EstadoPedido;
import com.saborgourmet.gestion.model.enums.EstadoRegistro;
import com.saborgourmet.gestion.model.enums.MetodoPago;
import com.saborgourmet.gestion.model.enums.Rol;
import com.saborgourmet.gestion.model.enums.TipoPlato;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/catalogos")
public class CatalogoApiController {

    @GetMapping("/roles")
    public Rol[] roles() {
        return Rol.values();
    }

    @GetMapping("/estados-mesa")
    public EstadoMesa[] estadosMesa() {
        return EstadoMesa.values();
    }

    @GetMapping("/estados-pedido")
    public EstadoPedido[] estadosPedido() {
        return EstadoPedido.values();
    }

    @GetMapping("/metodos-pago")
    public MetodoPago[] metodosPago() {
        return MetodoPago.values();
    }

    @GetMapping("/tipos-plato")
    public TipoPlato[] tiposPlato() {
        return TipoPlato.values();
    }

    @GetMapping("/estados-factura")
    public EstadoFactura[] estadosFactura() {
        return EstadoFactura.values();
    }

    @GetMapping("/estados-registro")
    public EstadoRegistro[] estadosRegistro() {
        return EstadoRegistro.values();
    }
}

