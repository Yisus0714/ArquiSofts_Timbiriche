
package vista.lobby;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Simulación de votación para iniciar una partida.
 *
 * Se utiliza únicamente en las pruebas del lobby.
 * No implementa comunicación LAN.
 */
public class VotacionInicio {

    private final Set<Integer> participantes;
    private final Map<Integer, Boolean> votos;

    public VotacionInicio(Set<Integer> participantes) {
        if (participantes == null
                || participantes.size() < 2
                || participantes.size() > 3
                || participantes.contains(null)) {
            throw new IllegalArgumentException(
                    "La votación requiere 2 o 3 participantes válidos."
            );
        }

        this.participantes = new HashSet<>(participantes);
        this.votos = new HashMap<>();
    }

    public void registrarVoto(int jugadorId, boolean acepta) {
        if (!participantes.contains(jugadorId)) {
            throw new IllegalArgumentException(
                    "El jugador no participa en esta votación."
            );
        }

        if (votos.containsKey(jugadorId)) {
            throw new IllegalStateException(
                    "El jugador ya emitió su voto."
            );
        }

        votos.put(jugadorId, acepta);
    }

    public boolean todosAceptaron() {
        return votos.size() == participantes.size()
                && votos.values().stream().allMatch(Boolean::booleanValue);
    }

    public boolean fueRechazada() {
        return votos.containsValue(false);
    }

    public boolean estaPendiente() {
        return !todosAceptaron() && !fueRechazada();
    }

    public int getVotosRegistrados() {
        return votos.size();
    }

    public int getTotalParticipantes() {
        return participantes.size();
    }
}
