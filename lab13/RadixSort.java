/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        // TODO: Implement LSD Sort
        int inputLength = asciis.length;
        String[] sorted = new String[inputLength];

        int maxStringLength = 0;
        for (int i = 0; i < inputLength; i += 1) {
            int l = asciis[i].length();
            if (l > maxStringLength) {
                maxStringLength = l;
            }
            sorted[i] = asciis[i];
        }
        // Sort from the right char
        int charIndex = maxStringLength - 1;
        while (charIndex >= 0) {
            int[] count = new int[256];
            String[] temp = new String[inputLength];
            // count String char at the same position, if there is no char count[0] += 1
            for (int i = 0; i < inputLength; i += 1) {
                if (sorted[i].length() < charIndex + 1) {
                    count[0] += 1;
                } else {
                    count[(int)sorted[i].charAt(charIndex)] += 1;
                }
                temp[i] = sorted[i];
            }
            // count[] change to position[]
            for (int i = 1; i < count.length; i += 1) {
                count[i] += count[i - 1];
            }

            for (int i = inputLength - 1; i >= 0; i -= 1) {
                if (temp[i].length() < charIndex + 1) {
                    sorted[count[0] - 1] = temp[i];
                    count[0] -= 1;
                } else {
                    sorted[count[(int)temp[i].charAt(charIndex)] - 1] = temp[i];
                    count[(int)temp[i].charAt(charIndex)] -= 1;
                }
            }
            charIndex -= 1;
        }
        return sorted;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        // Optional LSD helper method for required LSD radix sort
        return;
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }

    public static void main(String[] args) {
        String[] unsorted = {"elephant", "apple", "ball", "zoo", "lion", "cellphone", "window", "123", "cell"};
        String[] sorted = RadixSort.sort(unsorted);
        for (String s : unsorted) {
            System.out.print(s + " ");
        }
        System.out.println();
        for (String s : sorted) {
            System.out.print(s + " ");
        }
    }
}
