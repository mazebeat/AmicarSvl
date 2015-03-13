/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.intelidata.amicarsvl;

import cl.intelidata.amicarsvl.jpa.Clientes;
import cl.intelidata.amicarsvl.conf.EntityHelper;
import cl.intelidata.amicarsvl.jpa.Proceso;
import cl.intelidata.amicarsvl.util.Tools;
import javax.persistence.EntityManager;

/**
 *
 * @author Maze
 */
public class DB {

    public static void main(String... args) throws Exception {

        /**
         * EXAMPLE
         */
//        EntityManager em = null;
//
//        try {
//            em = EntityHelper.getInstance().getEntityManager();
//            // Toda la consulta aca
//            Clientes c = em.find(Clientes.class, 1);
//            System.out.println(c.getFonoParticular());
//
//        } catch (Exception e) {
//            throw new Exception("Error en consulta ", e);
//        } finally {
//            //necesario para cada consulta si queda abierto consume una conexion a la bd
//            if (em != null && em.isOpen()) {
//                if (em.getTransaction().isActive()) {
//                    em.getTransaction().rollback();
//                }
//                em.close();
//            }
//        }
    }

    private Proceso process(Integer iProcesoID) throws Exception {
        Proceso proceso = null;
        EntityManager em = null;

        try {
            em = EntityHelper.getInstance().getEntityManager();
            proceso = em.find(Proceso.class, iProcesoID);
//            List<Proceso> procesos = new ArrayList<Proceso>();
//            Query query = em.createQuery("SELECT p FROM Proceso p WHERE p.idProceso = :idProceso");
//            query.setParameter("idProceso", iProcesoID);
//            procesos = query.getResultList();
//
//            for (Proceso p : procesos) {
//                proceso = p;
//            }

        } catch (Exception e) {
            throw new Exception("Error en consulta ", e);
        } finally {
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }

        return proceso;
    }

    private void updateProcess(Proceso process, char what) throws Exception {
        EntityManager em = null;

        try {
            em = EntityHelper.getInstance().getEntityManager();
            em.getTransaction().begin();
            switch (what) {
                case 'c':
                    process.setFechaClickLink(Tools.nowDate());
                    break;
                case 'r':
                    process.setFechaAperturaMail(Tools.nowDate());
                    break;
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new Exception("Error en consulta ", e);
        } finally {
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
    }

    private Clientes client(Integer iClienteID) throws Exception {
        Clientes cliente = null;
        EntityManager em = null;

        try {
            em = EntityHelper.getInstance().getEntityManager();
            cliente
                    = em.find(Clientes.class, iClienteID);
        } catch (Exception e) {
            throw new Exception("Error en consulta ", e);
        } finally {
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }

        return cliente;
    }

    private void updateClient(Clientes client) throws Exception {
        EntityManager em = null;

        try {
            em = EntityHelper.getInstance().getEntityManager();
            em.getTransaction().begin();
            client.setDesinscrito(true);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new Exception("Error en consulta ", e);
        } finally {
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
    }

    public Clientes getCliente(Integer id) throws Exception {
        return this.client(id);
    }

    public Proceso getProceso(Integer id) throws Exception {
        return this.process(id);
    }

    /**
     * Actualiza proceso
     * <p>
     * @param proceso
     * @param queFecha char Puede ser r para actualizar fecha lectura o c para
     * la fecha click
     * @throws Exception
     */
    public void actualizarProceso(Proceso proceso, char queFecha) throws Exception {
        this.updateProcess(proceso, queFecha);
    }

    public void actualizarCliente(Clientes cliente) throws Exception {
        this.updateClient(cliente);
    }
}
