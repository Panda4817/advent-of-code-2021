package dev.kmunton.days.day16;

import dev.kmunton.days.Day;
import dev.kmunton.days.day15.Chiton;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Day16 implements Day {

    private List<String> bits;
    private long totalVersion;


    public Day16(String filename) {
        processData(filename);
    }

    @Override
    public void processData(String filename) {
        try {
            File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource(filename)).getFile());
            Scanner myReader = new Scanner(file);
            String s = myReader.nextLine();
            List<String> hexArray = List.of(s.split("(?<=\\G.)")).stream().collect(Collectors.toList());
            bits = new ArrayList<>();
            for (String h : hexArray) {
                String bitString = hexToBin(h);
                List<String> bitsArray = List.of(bitString.split("(?<=\\G.)"));
                bits.addAll(bitsArray);
            }
            myReader.close();
        } catch (
                FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private String hexToBin(String hex){
        hex = hex.replaceAll("0", "0000");
        hex = hex.replaceAll("1", "0001");
        hex = hex.replaceAll("2", "0010");
        hex = hex.replaceAll("3", "0011");
        hex = hex.replaceAll("4", "0100");
        hex = hex.replaceAll("5", "0101");
        hex = hex.replaceAll("6", "0110");
        hex = hex.replaceAll("7", "0111");
        hex = hex.replaceAll("8", "1000");
        hex = hex.replaceAll("9", "1001");
        hex = hex.replaceAll("A", "1010");
        hex = hex.replaceAll("B", "1011");
        hex = hex.replaceAll("C", "1100");
        hex = hex.replaceAll("D", "1101");
        hex = hex.replaceAll("E", "1110");
        hex = hex.replaceAll("F", "1111");
        return hex;
    }


    private long getBinToDecimal(List<String> bytes) {
        String binaryString = bytes.stream().reduce("", (s, a) -> s + a);
        long dec = Long.parseLong(binaryString, 2);
        return dec;
    }

    private long evaluate(List<Long> lst, Long type) throws Exception {
        long n;
        if ( type == 0) {
            n = lst.stream().mapToLong(l -> l).sum();
        } else if (type == 1) {
            n = lst.stream().reduce((long) 1, (a,b) -> a*b);
        } else if (type == 2) {
            n = lst.stream().mapToLong(l -> l).min().getAsLong();
        } else if (type == 3) {
            n = lst.stream().mapToLong(l -> l).max().getAsLong();
        } else if (type == 5) {
            if (lst.size() > 2) {
                throw new Exception("type id 5 but literal values more than 2");
            }

            if (lst.get(0) > lst.get(1)) {
                n = 1;
            } else {
                n = 0;
            }
        }else if (type == 6) {
            if (lst.size() > 2) {
                throw new Exception("type id 6 but literal values more than 2");
            }

            if (lst.get(0) < lst.get(1)) {
                n = 1;
            } else {
                n = 0;
            }
        }else {
            if (lst.size() > 2) {
                throw new Exception("type id 7 but literal values more than 2");
            }

            if (Objects.equals(lst.get(0), lst.get(1))) {
                n = 1;
            } else {
                n = 0;
            }
        }
        return n;
    }

    private Value getValue(int index) throws Exception {
        long version = getBinToDecimal(bits.subList(index, index + 3));
        totalVersion += version;
        index += 3;
        long type = getBinToDecimal(bits.subList(index, index + 3));
        index += 3;
        if (type == 4) {
            List<String> lst = new ArrayList<>();
            boolean last = false;
            while (!last) {
                if (Objects.equals(bits.get(index), "1")) {
                    index += 1;
                    lst.addAll(bits.subList(index, index + 4));
                    index += 4;
                } else if (Objects.equals(bits.get(index), "0")){
                    last = true;
                    index += 1;
                    lst.addAll(bits.subList(index, index + 4));
                    index += 4;
                }
            }
            long val = getBinToDecimal(lst);
            return new Value(val, index);
        } else {
            String mode = bits.get(index);
            index += 1;
            List<Long> subPacketValues = new ArrayList<>();
            if (Objects.equals(mode, "1")) {
                long totalPacket = getBinToDecimal(bits.subList(index, index + 11));
                index += 11;
                for (int i= 0; i< totalPacket; i++) {
                    Value obj = getValue(index);
                    subPacketValues.add(obj.getVal());
                    index = obj.getIndex();
                }
            } else {
                long subPacketlength = getBinToDecimal(bits.subList(index, index + 15));
                index += 15;
                long finalIndex = index + subPacketlength;
                while(index < finalIndex) {
                    Value obj = getValue(index);
                    subPacketValues.add(obj.getVal());
                    index = obj.getIndex();
                }


            }
            long val = evaluate(subPacketValues, type);
            return new Value(val, index);

        }
    }

    @Override
    public int part1()  {
        try{
            Value obj = getValue(0);
            System.out.print("part 2: ");
            System.out.println(obj.getVal());
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.print("part 1: ");
        System.out.println(totalVersion);
        return 0;
    }

    @Override
    public int part2() {

        return 0;
    }
}
