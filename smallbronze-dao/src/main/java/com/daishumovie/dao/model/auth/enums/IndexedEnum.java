package com.daishumovie.dao.model.auth.enums;


import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public interface IndexedEnum {
	public abstract int getIndex();

	public static final class IndexedEnumUtil {
		private static final Logger LOGGER = LoggerFactory.getLogger(IndexedEnumUtil.class);
		private static final int WARNNING_MAX_INDEX = 100;

		public static <E extends IndexedEnum> List<E> toIndexes(E[] enums) {
			Preconditions.checkState((enums != null) && (enums.length > 0), "The Array of Enum[%s] cannot be null or empty.",
					new Object[] { enums.getClass().getComponentType().getName() });

			int maxIndex = -2147483648;
			int curIdx = 0;

			for (E enm : enums) {
				curIdx = enm.getIndex();
				Preconditions.checkArgument(curIdx >= 0, "The index of Enum[%s] must be >= 0.", new Object[] { enm });
				if (curIdx > maxIndex) {
					maxIndex = curIdx;
				}
			}

			if (maxIndex >= WARNNING_MAX_INDEX) {
				LOGGER.warn("The index of Enum[{}] exceed threshold:{}.There is wasting memory probably.", enums.getClass()
						.getComponentType().getName(), Integer.valueOf(WARNNING_MAX_INDEX));
			}

			List<E> instances = new ArrayList<E>(maxIndex + 1);

			for (int i = 0; i < maxIndex + 1; i++) {
				instances.add(null);
			}
			for (E enm : enums) {
				curIdx = enm.getIndex();

				Preconditions.checkState(instances.get(curIdx) == null, "The index of Enum[%s] is not unique.", new Object[] { enums
						.getClass().getComponentType().getName() });

				instances.set(curIdx, enm);
			}
			return instances;
		}

		public static <E extends IndexedEnum> E valueOf(List<E> values, int index) {
			if ((index < 0) || (index >= values.size())) {
				return null;
			}
			return values.get(index);
		}
	}
}