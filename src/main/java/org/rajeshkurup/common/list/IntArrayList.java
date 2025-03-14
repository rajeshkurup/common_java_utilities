package org.rajeshkurup.common.list;

import java.util.ArrayList;

public class IntArrayList extends ArrayList<Integer> {

    public int findSubArraySize(int beginIdx, int target) {
        if(beginIdx < 0 || beginIdx >= this.size()) {
            throw new IllegalArgumentException("Invalid begin index: " + beginIdx);
        }
        if(target <= 0) {
            throw new IllegalArgumentException("Invalid target: " + target);
        }
        for(int idx = beginIdx, sum = 0; idx < this.size(); idx++) {
            sum += this.get(idx);
            if(sum >= target) {
                return idx + 1 - beginIdx;
            }
        }
        return 0;
    }

    public int findSmallestSubArraySize(int target) {
        int smallestSubArraySize = this.findSubArraySize(0, target);
        if(smallestSubArraySize > 0) {
            int subArraySize = smallestSubArraySize;
            for(int idx = 1; idx < this.size() && subArraySize > 0; idx++) {
                subArraySize = this.findSubArraySize(idx, target);
                if(subArraySize > 0 && subArraySize < smallestSubArraySize) {
                    smallestSubArraySize = subArraySize;
                }
            }
        }
        return smallestSubArraySize;
    }

}
