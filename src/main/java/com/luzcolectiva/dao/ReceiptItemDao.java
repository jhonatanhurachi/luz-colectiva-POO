package com.luzcolectiva.dao;

import com.luzcolectiva.modelo.ReceiptItem;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReceiptItemDao {

  Optional<ReceiptItem> findById(UUID id);

  List<ReceiptItem> findByReceiptId(UUID receiptId);

  void insert(ReceiptItem item);

  int update(ReceiptItem item);

  boolean deleteById(UUID id);
}
