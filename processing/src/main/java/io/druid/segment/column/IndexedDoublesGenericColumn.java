/*
 * Licensed to Metamarkets Group Inc. (Metamarkets) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. Metamarkets licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.druid.segment.column;

import io.druid.collections.bitmap.ImmutableBitmap;
import io.druid.query.monomorphicprocessing.RuntimeShapeInspector;
import io.druid.segment.ColumnValueSelector;
import io.druid.segment.data.IndexedDoubles;
import io.druid.segment.data.ReadableOffset;


public class IndexedDoublesGenericColumn implements GenericColumn
{
  private final IndexedDoubles column;
  private final ImmutableBitmap nullValueBitmap;

  public IndexedDoublesGenericColumn(IndexedDoubles indexedDoubles, ImmutableBitmap nullValueBitmap)
  {
    column = indexedDoubles;
    this.nullValueBitmap = nullValueBitmap;
  }

  @Override
  public int length()
  {
    return column.size();
  }

  @Override
  public ValueType getType()
  {
    return ValueType.DOUBLE;
  }

  @Override
  public String getStringSingleValueRow(int rowNum)
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public ColumnValueSelector makeColumnValueSelector(ReadableOffset offset)
  {
    return column.makeColumnValueSelector(offset, nullValueBitmap);
  }

  @Override
  public float getFloatSingleValueRow(int rowNum)
  {
    return (float) column.get(rowNum);
  }

  @Override
  public long getLongSingleValueRow(int rowNum)
  {
    return (long) column.get(rowNum);
  }

  @Override
  public double getDoubleSingleValueRow(int rowNum)
  {
    return column.get(rowNum);
  }

  @Override
  public boolean isNull(int rowNum)
  {
    return nullValueBitmap.get(rowNum);
  }

  public void close()
  {
    column.close();
  }

  @Override
  public void inspectRuntimeShape(RuntimeShapeInspector inspector)
  {
    inspector.visit("column", column);
  }
}
