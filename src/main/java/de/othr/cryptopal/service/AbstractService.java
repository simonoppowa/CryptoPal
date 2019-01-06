package de.othr.cryptopal.service;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.List;

public abstract class AbstractService<T> implements Serializable {

    @PersistenceContext
    protected EntityManager em;

    private Class<T> entityClass;

    public AbstractService(){
    }

    public AbstractService(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public T findById(Long id) {
        return em.find(entityClass, id);
    }

    public void remove(T entiy) {
        em.remove(em.merge(entiy));
    }

    public T merge(T entity) {
        return em.merge(entity);
    }

    public List<T> listAll(Integer startPosition, Integer maxResult)
    {
        TypedQuery<T> findAllQuery = getListAllQuery();
        if (startPosition != null)
        {
            findAllQuery.setFirstResult(startPosition);
        }
        if (maxResult != null)
        {
            findAllQuery.setMaxResults(maxResult);
        }
        return findAllQuery.getResultList();
    }

    public TypedQuery<T> getListAllQuery()
    {
        CriteriaQuery<T> criteria = em.getCriteriaBuilder().createQuery(entityClass);
        return em.createQuery(criteria.select(criteria.from(entityClass)));
    }

}