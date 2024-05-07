package org.example.quanlykhohang.dao;

import java.util.List;

public interface InterfaceDAO<T, ID> {
    public void create(T t);
    public void update(T t);
    public void delete(ID id);
    public T findById(ID id);
    public long count();
    public List<T> findAll();
//    public List<T> findAll(int page, int pageSize);
    public boolean existsById(ID id);
}
