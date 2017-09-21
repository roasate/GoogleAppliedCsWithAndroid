/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.Reader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Arrays;
public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    public int wordLength = DEFAULT_WORD_LENGTH;
;
    private Random random = new Random();
    public  ArrayList wordList = new ArrayList();
    public HashSet wordSet = new HashSet();
    HashMap<String, ArrayList<String>> lettersToWords = new HashMap<>();
    HashMap<Integer, ArrayList<String>> sizeToWords = new HashMap<>();



    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordList.add(word);
            wordSet.add(word);
            String word1 = sortLetters(word);
            if(!sizeToWords.containsKey(word.length()))
            {
                ArrayList wordList1 = new ArrayList();
                wordList1.add(word);
                sizeToWords.put(word.length(),wordList1);
            }
            else
            {
                ArrayList wordList1 = new ArrayList();
                wordList1 = (ArrayList) sizeToWords.get(word1.toString().length());
                wordList1.add(word);
                sizeToWords.put(word1.toString().length(),wordList1);

            }
            if(!lettersToWords.containsKey(word1))
            {
                ArrayList wordList1 = new ArrayList();
                wordList1.add(word);
                lettersToWords.put(word1,wordList1);
            }
            else
            {
                ArrayList wordList1 = new ArrayList();
                wordList1 = (ArrayList) lettersToWords.get(word1);
                wordList1.add(word);
                lettersToWords.put(word1,wordList1);

            }
            }
    }

    public boolean isGoodWord(String word, String base) {
        if(wordSet.contains(word) && !(base.contains(word)))
        return true;
        else
            return  false;
    }

    public List<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();
        String sorted_target = sortLetters(targetWord);
        String test;
        for(int i=0;i<wordList.size();i++)
        {
            test = wordList.get(i).toString();

             String test1 = sortLetters(test);
            if(test1 == sorted_target)
            {
                result.add(test);
            }

        }
        return result;
    }
    public static String sortLetters(String param)
    {
        char[] charArray = param.toCharArray();
        Arrays.sort(charArray);
        param =new String(charArray);
        return param;
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> tempList;
        ArrayList<String> resultList = new ArrayList<>();

        for (char alphabet = 'a'; alphabet <= 'z'; alphabet++) {
            String anagram = word + alphabet;
            String sortedAnagram = sortLetters(anagram);

            if (lettersToWords.containsKey(sortedAnagram)) {
                tempList = lettersToWords.get(sortedAnagram);

                for (int i = 0; i < tempList.size(); i++) {
                    if ( !(tempList.get(i).contains(word)) ) {
                        resultList.add(tempList.get(i));
                    }
                }
            }
        }

        return resultList;
    }

    public String pickGoodStarterWord() {
        boolean flag=true;
        int index=0;
        ArrayList search = new ArrayList();
        search = sizeToWords.get(wordLength);
        while (flag) {

            ArrayList tempList = new ArrayList();

            index = random.nextInt(search.size());
            tempList = getAnagramsWithOneMoreLetter(search.get(index).toString()
            );
            if(tempList.size()>MIN_NUM_ANAGRAMS)
            {
                flag=false;
            }
        }
        if(wordLength < MAX_WORD_LENGTH)
        wordLength ++;
        return search.get(index).toString();
    }
}
