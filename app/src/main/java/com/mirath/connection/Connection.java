package com.mirath.connection;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.koushikdutta.ion.Ion;
import com.mirath.models.Answer;
import com.mirath.models.Parser;
import com.mirath.models.Question;
import com.mirath.utils.SharedPrefUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Nour on 3/25/2018.
 */

public class Connection {

    public enum ErrorCodes {

        NO_CONTENT("204"),
        BAD_REQUEST("400");

        String code;

        ErrorCodes(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    public static void getQuestions(Context context, final ConnectionDelegate connectionDelegate) {

        Ion.with(context)
                .load(APIEndPoints.QUESTIONS_API)
                .addHeader("language", SharedPrefUtils.getLanguage())
                .asString()
                .withResponse()
                .setCallback((e, result) -> {
                    if (e == null && result.getHeaders().code() == 200) {
                        try {
                            Log.d("conn", "result gotten!");

                            new Parser.ParseQuestionsTask(new JSONArray(result.getResult()), new ConnectionDelegate() {

                                @Override
                                public void onConnectionSuccess(ArrayList<Question> questions, ArrayList<Answer> answers) {
                                    connectionDelegate.onConnectionSuccess(questions, null);
                                }

                                @Override
                                public void onConnectionFailed(String code) {
                                    connectionDelegate.onConnectionFailed(ErrorCodes.BAD_REQUEST.getCode());

                                }
                            }).execute();

                        } catch (JSONException e1) {
                            e1.printStackTrace();
                            connectionDelegate.onConnectionFailed(ErrorCodes.BAD_REQUEST.getCode());

                        }
                    } else {
                        if (e == null)
                            connectionDelegate.onConnectionFailed(result.getHeaders().code() + "");
                        else
                            connectionDelegate.onConnectionFailed(400 + "");
                    }
                });
    }

    public static void demoGetQuestions(Context context, final ConnectionDelegate connectionDelegate) throws JSONException {

        String demoString = "[\n" +
                "    {\n" +
                "        \"id\": 21,\n" +
                "        \"question\": \"ما هو مقدار التركة؟\",\n" +
                "        \"question_en\": \"\",\n" +
                "        \"desc\": \"\",\n" +
                "        \"desc_en\": \"\",\n" +
                "        \"type_id\": 3,\n" +
                "        \"parent\": null,\n" +
                "        \"pp\": -1,\n" +
                "        \"symbol\": \"Heritage\",\n" +
                "        \"group_id\": 1,\n" +
                "        \"default_answer\": 0,\n" +
                "        \"visible\": true,\n" +
                "        \"type\": {\n" +
                "            \"id\": 3,\n" +
                "            \"name\": \"NUMBER\"\n" +
                "        },\n" +
                "        \"choices\": []\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 22,\n" +
                "        \"question\": \"What is the gender of the deceased ?\",\n" +
                "        \"question_en\": \"What is the gender of the deceased ?\",\n" +
                "        \"desc\": \"\",\n" +
                "        \"desc_en\": \"\",\n" +
                "        \"type_id\": 2,\n" +
                "        \"parent\": null,\n" +
                "        \"pp\": -1,\n" +
                "        \"symbol\": \"Gender\",\n" +
                "        \"group_id\": 1,\n" +
                "        \"default_answer\": 1,\n" +
                "        \"visible\": true,\n" +
                "        \"type\": {\n" +
                "            \"id\": 2,\n" +
                "            \"name\": \"CHOICE\"\n" +
                "        },\n" +
                "        \"choices\": [\n" +
                "            {\n" +
                "                \"id\": 10,\n" +
                "                \"text\": \"Male\",\n" +
                "                \"text_en\": \"Male\",\n" +
                "                \"value\": 1,\n" +
                "                \"question_id\": 22\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 11,\n" +
                "                \"text\": \"Female\",\n" +
                "                \"text_en\": \"Female\",\n" +
                "                \"value\": 2,\n" +
                "                \"question_id\": 22\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 23,\n" +
                "        \"question\": \"What is the marital status ?\",\n" +
                "        \"question_en\": \"What is the marital status ?\",\n" +
                "        \"desc\": \"\",\n" +
                "        \"desc_en\": \"\",\n" +
                "        \"type_id\": 2,\n" +
                "        \"parent\": null,\n" +
                "        \"pp\": -1,\n" +
                "        \"symbol\": \"Status\",\n" +
                "        \"group_id\": 1,\n" +
                "        \"default_answer\": 2,\n" +
                "        \"visible\": true,\n" +
                "        \"type\": {\n" +
                "            \"id\": 2,\n" +
                "            \"name\": \"CHOICE\"\n" +
                "        },\n" +
                "        \"choices\": [\n" +
                "            {\n" +
                "                \"id\": 12,\n" +
                "                \"text\": \"متزوج/ة\",\n" +
                "                \"text_en\": \"\",\n" +
                "                \"value\": 1,\n" +
                "                \"question_id\": 23\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 15,\n" +
                "                \"text\": \"غير متزوّج/ة\",\n" +
                "                \"text_en\": \"\",\n" +
                "                \"value\": 2,\n" +
                "                \"question_id\": 23\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 16,\n" +
                "                \"text\": \"أرمل/ة\",\n" +
                "                \"text_en\": \"\",\n" +
                "                \"value\": 3,\n" +
                "                \"question_id\": 23\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 26,\n" +
                "        \"question\": \"Is the deceased's father alive ?\",\n" +
                "        \"question_en\": \"Is the deceased's father alive ?\",\n" +
                "        \"desc\": \"\",\n" +
                "        \"desc_en\": \"\",\n" +
                "        \"type_id\": 1,\n" +
                "        \"parent\": null,\n" +
                "        \"pp\": -1,\n" +
                "        \"symbol\": \"Father\",\n" +
                "        \"group_id\": 1,\n" +
                "        \"default_answer\": 0,\n" +
                "        \"visible\": true,\n" +
                "        \"type\": {\n" +
                "            \"id\": 1,\n" +
                "            \"name\": \"YN\"\n" +
                "        },\n" +
                "        \"choices\": []\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 27,\n" +
                "        \"question\": \"Is the deceased's mother alive ?\",\n" +
                "        \"question_en\": \"Is the deceased's mother alive ?\",\n" +
                "        \"desc\": \"\",\n" +
                "        \"desc_en\": \"\",\n" +
                "        \"type_id\": 1,\n" +
                "        \"parent\": null,\n" +
                "        \"pp\": -1,\n" +
                "        \"symbol\": \"Mother\",\n" +
                "        \"group_id\": 1,\n" +
                "        \"default_answer\": 0,\n" +
                "        \"visible\": true,\n" +
                "        \"type\": {\n" +
                "            \"id\": 1,\n" +
                "            \"name\": \"YN\"\n" +
                "        },\n" +
                "        \"choices\": []\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 60,\n" +
                "        \"question\": \"Wives count\",\n" +
                "        \"question_en\": \"Wives count\",\n" +
                "        \"desc\": \"\",\n" +
                "        \"desc_en\": \"\",\n" +
                "        \"type_id\": 3,\n" +
                "        \"parent\": 23,\n" +
                "        \"pp\": 1,\n" +
                "        \"symbol\": \"Wives\",\n" +
                "        \"group_id\": 1,\n" +
                "        \"default_answer\": 0,\n" +
                "        \"visible\": false,\n" +
                "        \"type\": {\n" +
                "            \"id\": 3,\n" +
                "            \"name\": \"NUMBER\"\n" +
                "        },\n" +
                "        \"choices\": []\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 62,\n" +
                "        \"question\": \"Does he have sons\",\n" +
                "        \"question_en\": \"Does he have sons\",\n" +
                "        \"desc\": \"No matter if alive or not\",\n" +
                "        \"desc_en\": \"No matter if alive or not\",\n" +
                "        \"type_id\": 1,\n" +
                "        \"parent\": null,\n" +
                "        \"pp\": null,\n" +
                "        \"symbol\": \"SonsBool\",\n" +
                "        \"group_id\": 1,\n" +
                "        \"default_answer\": 0,\n" +
                "        \"visible\": true,\n" +
                "        \"type\": {\n" +
                "            \"id\": 1,\n" +
                "            \"name\": \"YN\"\n" +
                "        },\n" +
                "        \"choices\": []\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 63,\n" +
                "        \"question\": \"How many alive Sons ?\",\n" +
                "        \"question_en\": \"How many alive Sons ?\",\n" +
                "        \"desc\": \"\",\n" +
                "        \"desc_en\": \"\",\n" +
                "        \"type_id\": 3,\n" +
                "        \"parent\": 62,\n" +
                "        \"pp\": 1,\n" +
                "        \"symbol\": \"Sons\",\n" +
                "        \"group_id\": 1,\n" +
                "        \"default_answer\": 0,\n" +
                "        \"visible\": false,\n" +
                "        \"type\": {\n" +
                "            \"id\": 3,\n" +
                "            \"name\": \"NUMBER\"\n" +
                "        },\n" +
                "        \"choices\": []\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 64,\n" +
                "        \"question\": \"Does he have daughters?\",\n" +
                "        \"question_en\": \"Does he have daughters?\",\n" +
                "        \"desc\": \"No matter if alive or not\",\n" +
                "        \"desc_en\": \"No matter if alive or not\",\n" +
                "        \"type_id\": 1,\n" +
                "        \"parent\": null,\n" +
                "        \"pp\": null,\n" +
                "        \"symbol\": \"DaughtersBool\",\n" +
                "        \"group_id\": 1,\n" +
                "        \"default_answer\": 0,\n" +
                "        \"visible\": true,\n" +
                "        \"type\": {\n" +
                "            \"id\": 1,\n" +
                "            \"name\": \"YN\"\n" +
                "        },\n" +
                "        \"choices\": []\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 65,\n" +
                "        \"question\": \"How many alive daughters?\",\n" +
                "        \"question_en\": \"How many alive daughters?\",\n" +
                "        \"desc\": \"\",\n" +
                "        \"desc_en\": \"\",\n" +
                "        \"type_id\": 3,\n" +
                "        \"parent\": 64,\n" +
                "        \"pp\": 1,\n" +
                "        \"symbol\": \"Daughters\",\n" +
                "        \"group_id\": 1,\n" +
                "        \"default_answer\": 0,\n" +
                "        \"visible\": false,\n" +
                "        \"type\": {\n" +
                "            \"id\": 3,\n" +
                "            \"name\": \"NUMBER\"\n" +
                "        },\n" +
                "        \"choices\": []\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 66,\n" +
                "        \"question\": \"Does he have full brothers?\",\n" +
                "        \"question_en\": \"Does he have full brothers?\",\n" +
                "        \"desc\": \"No matter if alive or not\",\n" +
                "        \"desc_en\": \"No matter if alive or not\",\n" +
                "        \"type_id\": 1,\n" +
                "        \"parent\": 26,\n" +
                "        \"pp\": 0,\n" +
                "        \"symbol\": \"FullBrothersBool\",\n" +
                "        \"group_id\": 1,\n" +
                "        \"default_answer\": 0,\n" +
                "        \"visible\": false,\n" +
                "        \"type\": {\n" +
                "            \"id\": 1,\n" +
                "            \"name\": \"YN\"\n" +
                "        },\n" +
                "        \"choices\": []\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 67,\n" +
                "        \"question\": \"How many alive full brothers?\",\n" +
                "        \"question_en\": \"How many alive full brothers?\",\n" +
                "        \"desc\": \"From the same father and mother\",\n" +
                "        \"desc_en\": \"From the same father and mother\",\n" +
                "        \"type_id\": 3,\n" +
                "        \"parent\": 66,\n" +
                "        \"pp\": 1,\n" +
                "        \"symbol\": \"FullBrothers\",\n" +
                "        \"group_id\": 1,\n" +
                "        \"default_answer\": 0,\n" +
                "        \"visible\": false,\n" +
                "        \"type\": {\n" +
                "            \"id\": 3,\n" +
                "            \"name\": \"NUMBER\"\n" +
                "        },\n" +
                "        \"choices\": []\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 68,\n" +
                "        \"question\": \"Does he have full sisters?\",\n" +
                "        \"question_en\": \"Does he have full sisters?\",\n" +
                "        \"desc\": \"From the same father and mother\",\n" +
                "        \"desc_en\": \"From the same father and mother\",\n" +
                "        \"type_id\": 1,\n" +
                "        \"parent\": 26,\n" +
                "        \"pp\": 0,\n" +
                "        \"symbol\": \"FullSistersBool\",\n" +
                "        \"group_id\": 1,\n" +
                "        \"default_answer\": 0,\n" +
                "        \"visible\": false,\n" +
                "        \"type\": {\n" +
                "            \"id\": 1,\n" +
                "            \"name\": \"YN\"\n" +
                "        },\n" +
                "        \"choices\": []\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 69,\n" +
                "        \"question\": \"How many alive full sisters?\",\n" +
                "        \"question_en\": \"How many alive full sisters?\",\n" +
                "        \"desc\": \"From the same father and mother\",\n" +
                "        \"desc_en\": \"From the same father and mother\",\n" +
                "        \"type_id\": 3,\n" +
                "        \"parent\": 68,\n" +
                "        \"pp\": 1,\n" +
                "        \"symbol\": \"FullSisters\",\n" +
                "        \"group_id\": 1,\n" +
                "        \"default_answer\": 0,\n" +
                "        \"visible\": false,\n" +
                "        \"type\": {\n" +
                "            \"id\": 3,\n" +
                "            \"name\": \"NUMBER\"\n" +
                "        },\n" +
                "        \"choices\": []\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 70,\n" +
                "        \"question\": \"هل للمتوفَّى ابن ابن(وإن دنا)؟\",\n" +
                "        \"question_en\": \"\",\n" +
                "        \"desc\": \"ابن الابن - ابن ابن الابن - ابن ابن الابن .. \",\n" +
                "        \"desc_en\": \"\",\n" +
                "        \"type_id\": 1,\n" +
                "        \"parent\": 63,\n" +
                "        \"pp\": 1,\n" +
                "        \"symbol\": \"GrandsonsBool\",\n" +
                "        \"group_id\": 1,\n" +
                "        \"default_answer\": 0,\n" +
                "        \"visible\": false,\n" +
                "        \"type\": {\n" +
                "            \"id\": 1,\n" +
                "            \"name\": \"YN\"\n" +
                "        },\n" +
                "        \"choices\": []\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 71,\n" +
                "        \"question\": \"كم عدد أبناء الأبناء الذكور(على قيد الحياة)؟\",\n" +
                "        \"question_en\": \"\",\n" +
                "        \"desc\": \"من نفس سوية القرابة.\",\n" +
                "        \"desc_en\": \"\",\n" +
                "        \"type_id\": 3,\n" +
                "        \"parent\": 70,\n" +
                "        \"pp\": 1,\n" +
                "        \"symbol\": \"Grandsons\",\n" +
                "        \"group_id\": 1,\n" +
                "        \"default_answer\": 0,\n" +
                "        \"visible\": false,\n" +
                "        \"type\": {\n" +
                "            \"id\": 3,\n" +
                "            \"name\": \"NUMBER\"\n" +
                "        },\n" +
                "        \"choices\": []\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 72,\n" +
                "        \"question\": \"هل للمتوفّى ابنة ابن (وإن دنت)؟\",\n" +
                "        \"question_en\": \"\",\n" +
                "        \"desc\": \"بنت ابن - بنت ابن الابن ..\",\n" +
                "        \"desc_en\": \"\",\n" +
                "        \"type_id\": 1,\n" +
                "        \"parent\": 63,\n" +
                "        \"pp\": 1,\n" +
                "        \"symbol\": \"GrandDaughtersBool\",\n" +
                "        \"group_id\": 1,\n" +
                "        \"default_answer\": 0,\n" +
                "        \"visible\": false,\n" +
                "        \"type\": {\n" +
                "            \"id\": 1,\n" +
                "            \"name\": \"YN\"\n" +
                "        },\n" +
                "        \"choices\": []\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 73,\n" +
                "        \"question\": \"هل للمتوفّى ابن أخ شقيق (وإن دنا)؟\",\n" +
                "        \"question_en\": \"\",\n" +
                "        \"desc\": \"ابن اخ - ابن ابن الأخ .. \",\n" +
                "        \"desc_en\": \"\",\n" +
                "        \"type_id\": 1,\n" +
                "        \"parent\": 67,\n" +
                "        \"pp\": 0,\n" +
                "        \"symbol\": \"FullNephewsBool\",\n" +
                "        \"group_id\": 1,\n" +
                "        \"default_answer\": 0,\n" +
                "        \"visible\": false,\n" +
                "        \"type\": {\n" +
                "            \"id\": 1,\n" +
                "            \"name\": \"YN\"\n" +
                "        },\n" +
                "        \"choices\": []\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 74,\n" +
                "        \"question\": \"هل للمتوفّى أخوة من أب؟\",\n" +
                "        \"question_en\": \"\",\n" +
                "        \"desc\": \"إن كانوا على قيد الحياة أو لا\",\n" +
                "        \"desc_en\": \"\",\n" +
                "        \"type_id\": 1,\n" +
                "        \"parent\": 66,\n" +
                "        \"pp\": 0,\n" +
                "        \"symbol\": \"PaternalBrothersBool\",\n" +
                "        \"group_id\": 1,\n" +
                "        \"default_answer\": 0,\n" +
                "        \"visible\": false,\n" +
                "        \"type\": {\n" +
                "            \"id\": 1,\n" +
                "            \"name\": \"YN\"\n" +
                "        },\n" +
                "        \"choices\": []\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 75,\n" +
                "        \"question\": \"كم عدد الأخوة من أب على قيد الحياة؟\",\n" +
                "        \"question_en\": \"\",\n" +
                "        \"desc\": \"\",\n" +
                "        \"desc_en\": \"\",\n" +
                "        \"type_id\": 3,\n" +
                "        \"parent\": 74,\n" +
                "        \"pp\": 1,\n" +
                "        \"symbol\": \"PaternalBrothers\",\n" +
                "        \"group_id\": 1,\n" +
                "        \"default_answer\": 0,\n" +
                "        \"visible\": false,\n" +
                "        \"type\": {\n" +
                "            \"id\": 3,\n" +
                "            \"name\": \"NUMBER\"\n" +
                "        },\n" +
                "        \"choices\": []\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 76,\n" +
                "        \"question\": \"هل للمتوفًّى أخوات لأب؟\",\n" +
                "        \"question_en\": \"\",\n" +
                "        \"desc\": \"\",\n" +
                "        \"desc_en\": \"\",\n" +
                "        \"type_id\": 1,\n" +
                "        \"parent\": 69,\n" +
                "        \"pp\": 0,\n" +
                "        \"symbol\": \"PaternalSistersBool\",\n" +
                "        \"group_id\": 1,\n" +
                "        \"default_answer\": 0,\n" +
                "        \"visible\": false,\n" +
                "        \"type\": {\n" +
                "            \"id\": 1,\n" +
                "            \"name\": \"YN\"\n" +
                "        },\n" +
                "        \"choices\": []\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 77,\n" +
                "        \"question\": \"كم عدد الأخوات لأب على قيد الحياة؟\",\n" +
                "        \"question_en\": \"\",\n" +
                "        \"desc\": \"\",\n" +
                "        \"desc_en\": \"\",\n" +
                "        \"type_id\": 3,\n" +
                "        \"parent\": 76,\n" +
                "        \"pp\": 1,\n" +
                "        \"symbol\": \"PaternalSisters\",\n" +
                "        \"group_id\": 1,\n" +
                "        \"default_answer\": 0,\n" +
                "        \"visible\": false,\n" +
                "        \"type\": {\n" +
                "            \"id\": 3,\n" +
                "            \"name\": \"NUMBER\"\n" +
                "        },\n" +
                "        \"choices\": []\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 78,\n" +
                "        \"question\": \"هل للمتوفّى أخوة لأم؟\",\n" +
                "        \"question_en\": \"\",\n" +
                "        \"desc\": \"\",\n" +
                "        \"desc_en\": \"\",\n" +
                "        \"type_id\": 1,\n" +
                "        \"parent\": 26,\n" +
                "        \"pp\": 0,\n" +
                "        \"symbol\": \"MaternalBrothersBool\",\n" +
                "        \"group_id\": 1,\n" +
                "        \"default_answer\": 0,\n" +
                "        \"visible\": false,\n" +
                "        \"type\": {\n" +
                "            \"id\": 1,\n" +
                "            \"name\": \"YN\"\n" +
                "        },\n" +
                "        \"choices\": []\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 79,\n" +
                "        \"question\": \"كم عدد الأخوة لأم على قيد الحياة؟\",\n" +
                "        \"question_en\": \"\",\n" +
                "        \"desc\": \"\",\n" +
                "        \"desc_en\": \"\",\n" +
                "        \"type_id\": 3,\n" +
                "        \"parent\": 78,\n" +
                "        \"pp\": 1,\n" +
                "        \"symbol\": \"MaternalBrothers\",\n" +
                "        \"group_id\": 1,\n" +
                "        \"default_answer\": 0,\n" +
                "        \"visible\": false,\n" +
                "        \"type\": {\n" +
                "            \"id\": 3,\n" +
                "            \"name\": \"NUMBER\"\n" +
                "        },\n" +
                "        \"choices\": []\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 80,\n" +
                "        \"question\": \"هل للمتوفى أخوات لأم؟\",\n" +
                "        \"question_en\": \"\",\n" +
                "        \"desc\": \"\",\n" +
                "        \"desc_en\": \"\",\n" +
                "        \"type_id\": 1,\n" +
                "        \"parent\": 69,\n" +
                "        \"pp\": 0,\n" +
                "        \"symbol\": \"MaternalSistersBool\",\n" +
                "        \"group_id\": 1,\n" +
                "        \"default_answer\": 0,\n" +
                "        \"visible\": false,\n" +
                "        \"type\": {\n" +
                "            \"id\": 1,\n" +
                "            \"name\": \"YN\"\n" +
                "        },\n" +
                "        \"choices\": []\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 81,\n" +
                "        \"question\": \"كم عدد الأخوات لأم على قيد الحياة؟\",\n" +
                "        \"question_en\": \"\",\n" +
                "        \"desc\": \"\",\n" +
                "        \"desc_en\": \"\",\n" +
                "        \"type_id\": 3,\n" +
                "        \"parent\": 80,\n" +
                "        \"pp\": 1,\n" +
                "        \"symbol\": \"MaternalSisters\",\n" +
                "        \"group_id\": 1,\n" +
                "        \"default_answer\": 0,\n" +
                "        \"visible\": false,\n" +
                "        \"type\": {\n" +
                "            \"id\": 3,\n" +
                "            \"name\": \"NUMBER\"\n" +
                "        },\n" +
                "        \"choices\": []\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 82,\n" +
                "        \"question\": \"هل للمتوفّى جد (وإن علا) على قيد الحياة؟\",\n" +
                "        \"question_en\": \"\",\n" +
                "        \"desc\": \"أب الأب - أب أب الأب - أب أب الأب (وإن علا) ..\",\n" +
                "        \"desc_en\": \"\",\n" +
                "        \"type_id\": 1,\n" +
                "        \"parent\": 26,\n" +
                "        \"pp\": 0,\n" +
                "        \"symbol\": \"Grandfather\",\n" +
                "        \"group_id\": 1,\n" +
                "        \"default_answer\": 0,\n" +
                "        \"visible\": false,\n" +
                "        \"type\": {\n" +
                "            \"id\": 1,\n" +
                "            \"name\": \"YN\"\n" +
                "        },\n" +
                "        \"choices\": []\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 83,\n" +
                "        \"question\": \"هل للمتوفّى جدّة لأب (وإن علت)؟\",\n" +
                "        \"question_en\": \"\",\n" +
                "        \"desc\": \"أم الأب - أم أم الأب - أم أب الأب ..\",\n" +
                "        \"desc_en\": \"\",\n" +
                "        \"type_id\": 1,\n" +
                "        \"parent\": 26,\n" +
                "        \"pp\": 0,\n" +
                "        \"symbol\": \"PaternalGrandmother\",\n" +
                "        \"group_id\": 1,\n" +
                "        \"default_answer\": 0,\n" +
                "        \"visible\": false,\n" +
                "        \"type\": {\n" +
                "            \"id\": 1,\n" +
                "            \"name\": \"YN\"\n" +
                "        },\n" +
                "        \"choices\": []\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 84,\n" +
                "        \"question\": \"هل للمتوفّى جدة لأم (وإن علت) على قيد الحياة؟\",\n" +
                "        \"question_en\": \"\",\n" +
                "        \"desc\": \"\",\n" +
                "        \"desc_en\": \"\",\n" +
                "        \"type_id\": 1,\n" +
                "        \"parent\": 27,\n" +
                "        \"pp\": 0,\n" +
                "        \"symbol\": \"MaternalGrandmother\",\n" +
                "        \"group_id\": 1,\n" +
                "        \"default_answer\": 0,\n" +
                "        \"visible\": false,\n" +
                "        \"type\": {\n" +
                "            \"id\": 1,\n" +
                "            \"name\": \"YN\"\n" +
                "        },\n" +
                "        \"choices\": []\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 85,\n" +
                "        \"question\": \"هل للمتوفّى عم حقيقي؟\",\n" +
                "        \"question_en\": \"\",\n" +
                "        \"desc\": \"\",\n" +
                "        \"desc_en\": \"\",\n" +
                "        \"type_id\": 1,\n" +
                "        \"parent\": 26,\n" +
                "        \"pp\": 0,\n" +
                "        \"symbol\": \"FullUnclesBool\",\n" +
                "        \"group_id\": 1,\n" +
                "        \"default_answer\": 0,\n" +
                "        \"visible\": false,\n" +
                "        \"type\": {\n" +
                "            \"id\": 1,\n" +
                "            \"name\": \"YN\"\n" +
                "        },\n" +
                "        \"choices\": []\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 86,\n" +
                "        \"question\": \"كم عدد الأعمام الحقيقيين على قيد الحياة؟\",\n" +
                "        \"question_en\": \"\",\n" +
                "        \"desc\": \"العموم الحقيقين.\",\n" +
                "        \"desc_en\": \"\",\n" +
                "        \"type_id\": 3,\n" +
                "        \"parent\": 85,\n" +
                "        \"pp\": 1,\n" +
                "        \"symbol\": \"FullUncles\",\n" +
                "        \"group_id\": 1,\n" +
                "        \"default_answer\": 0,\n" +
                "        \"visible\": false,\n" +
                "        \"type\": {\n" +
                "            \"id\": 3,\n" +
                "            \"name\": \"NUMBER\"\n" +
                "        },\n" +
                "        \"choices\": []\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 87,\n" +
                "        \"question\": \"هل للمتوفى ابن عم حقيقي(وإن دنا)؟\",\n" +
                "        \"question_en\": \"\",\n" +
                "        \"desc\": \"\",\n" +
                "        \"desc_en\": \"\",\n" +
                "        \"type_id\": 1,\n" +
                "        \"parent\": 86,\n" +
                "        \"pp\": 0,\n" +
                "        \"symbol\": \"FullCousinsBool\",\n" +
                "        \"group_id\": 1,\n" +
                "        \"default_answer\": 0,\n" +
                "        \"visible\": false,\n" +
                "        \"type\": {\n" +
                "            \"id\": 1,\n" +
                "            \"name\": \"YN\"\n" +
                "        },\n" +
                "        \"choices\": []\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 88,\n" +
                "        \"question\": \"كم عدد أبناء الأعمام الحقيقين  وعلى قيد الحياة؟\",\n" +
                "        \"question_en\": \"\",\n" +
                "        \"desc\": \"شرط كونهم في نفس المستوى من القرابة\",\n" +
                "        \"desc_en\": \"\",\n" +
                "        \"type_id\": 3,\n" +
                "        \"parent\": 87,\n" +
                "        \"pp\": 1,\n" +
                "        \"symbol\": \"FullCousins\",\n" +
                "        \"group_id\": 1,\n" +
                "        \"default_answer\": 0,\n" +
                "        \"visible\": false,\n" +
                "        \"type\": {\n" +
                "            \"id\": 3,\n" +
                "            \"name\": \"NUMBER\"\n" +
                "        },\n" +
                "        \"choices\": []\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 89,\n" +
                "        \"question\": \"هل للمتوفّى عم أخ لأب؟\",\n" +
                "        \"question_en\": \"\",\n" +
                "        \"desc\": \"\",\n" +
                "        \"desc_en\": \"\",\n" +
                "        \"type_id\": 1,\n" +
                "        \"parent\": 85,\n" +
                "        \"pp\": 0,\n" +
                "        \"symbol\": \"PaternalUnclesBool\",\n" +
                "        \"group_id\": 1,\n" +
                "        \"default_answer\": 0,\n" +
                "        \"visible\": false,\n" +
                "        \"type\": {\n" +
                "            \"id\": 1,\n" +
                "            \"name\": \"YN\"\n" +
                "        },\n" +
                "        \"choices\": []\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 90,\n" +
                "        \"question\": \"هل للمتوفى ابن أخ لأب (وإن دنا)؟\",\n" +
                "        \"question_en\": \"\",\n" +
                "        \"desc\": \"\",\n" +
                "        \"desc_en\": \"\",\n" +
                "        \"type_id\": 1,\n" +
                "        \"parent\": 75,\n" +
                "        \"pp\": 0,\n" +
                "        \"symbol\": \"PaternalNephewsBool\",\n" +
                "        \"group_id\": 1,\n" +
                "        \"default_answer\": 0,\n" +
                "        \"visible\": false,\n" +
                "        \"type\": {\n" +
                "            \"id\": 1,\n" +
                "            \"name\": \"YN\"\n" +
                "        },\n" +
                "        \"choices\": []\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 91,\n" +
                "        \"question\": \"كم عدد أبناء الأخ لأب (وإن  دنوا) على قيد الحياة؟\",\n" +
                "        \"question_en\": \"\",\n" +
                "        \"desc\": \"شرط كونهم في نفس المستوى من القرابة\",\n" +
                "        \"desc_en\": \"\",\n" +
                "        \"type_id\": 3,\n" +
                "        \"parent\": 90,\n" +
                "        \"pp\": 1,\n" +
                "        \"symbol\": \"PaternalNephews\",\n" +
                "        \"group_id\": 1,\n" +
                "        \"default_answer\": 0,\n" +
                "        \"visible\": false,\n" +
                "        \"type\": {\n" +
                "            \"id\": 3,\n" +
                "            \"name\": \"NUMBER\"\n" +
                "        },\n" +
                "        \"choices\": []\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 92,\n" +
                "        \"question\": \"كم عدد أبناء الأخوة الأشقاء (وإن دنوا) على قيد الحياة؟\",\n" +
                "        \"question_en\": \"\",\n" +
                "        \"desc\": \"شرط كونهم في نفس المستوى من القرابة\",\n" +
                "        \"desc_en\": \"\",\n" +
                "        \"type_id\": 3,\n" +
                "        \"parent\": 73,\n" +
                "        \"pp\": 1,\n" +
                "        \"symbol\": \"FullNephews\",\n" +
                "        \"group_id\": 1,\n" +
                "        \"default_answer\": 0,\n" +
                "        \"visible\": false,\n" +
                "        \"type\": {\n" +
                "            \"id\": 3,\n" +
                "            \"name\": \"NUMBER\"\n" +
                "        },\n" +
                "        \"choices\": []\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 93,\n" +
                "        \"question\": \"كم عدد العموم الأخوة لأب على قيد الحياة؟\",\n" +
                "        \"question_en\": \"\",\n" +
                "        \"desc\": \"\",\n" +
                "        \"desc_en\": \"\",\n" +
                "        \"type_id\": 3,\n" +
                "        \"parent\": 89,\n" +
                "        \"pp\": 1,\n" +
                "        \"symbol\": \"PaternalUncles\",\n" +
                "        \"group_id\": 1,\n" +
                "        \"default_answer\": 0,\n" +
                "        \"visible\": false,\n" +
                "        \"type\": {\n" +
                "            \"id\": 3,\n" +
                "            \"name\": \"NUMBER\"\n" +
                "        },\n" +
                "        \"choices\": []\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 94,\n" +
                "        \"question\": \"هل للمتوفى أبناء عم أخ لأب ؟\",\n" +
                "        \"question_en\": \"\",\n" +
                "        \"desc\": \"\",\n" +
                "        \"desc_en\": \"\",\n" +
                "        \"type_id\": 1,\n" +
                "        \"parent\": 89,\n" +
                "        \"pp\": 1,\n" +
                "        \"symbol\": \"PaternalCousinsBool\",\n" +
                "        \"group_id\": 1,\n" +
                "        \"default_answer\": 0,\n" +
                "        \"visible\": false,\n" +
                "        \"type\": {\n" +
                "            \"id\": 1,\n" +
                "            \"name\": \"YN\"\n" +
                "        },\n" +
                "        \"choices\": []\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 95,\n" +
                "        \"question\": \"كم عدد أبناء العموم الأخوة لأب (وإن دنوا) على قيد الحياة؟\",\n" +
                "        \"question_en\": \"\",\n" +
                "        \"desc\": \"شرط كونهم في نفس المستوى من القرابة\",\n" +
                "        \"desc_en\": \"\",\n" +
                "        \"type_id\": 3,\n" +
                "        \"parent\": 94,\n" +
                "        \"pp\": 1,\n" +
                "        \"symbol\": \"PaternalCousins\",\n" +
                "        \"group_id\": 1,\n" +
                "        \"default_answer\": 0,\n" +
                "        \"visible\": false,\n" +
                "        \"type\": {\n" +
                "            \"id\": 3,\n" +
                "            \"name\": \"NUMBER\"\n" +
                "        },\n" +
                "        \"choices\": []\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 96,\n" +
                "        \"question\": \"Is the deceased's husband alive ?\",\n" +
                "        \"question_en\": \"Is the deceased's husband alive ?\",\n" +
                "        \"desc\": \"\",\n" +
                "        \"desc_en\": \"\",\n" +
                "        \"type_id\": 1,\n" +
                "        \"parent\": 23,\n" +
                "        \"pp\": -1,\n" +
                "        \"symbol\": \"Husband\",\n" +
                "        \"group_id\": 1,\n" +
                "        \"default_answer\": 0,\n" +
                "        \"visible\": false,\n" +
                "        \"type\": {\n" +
                "            \"id\": 1,\n" +
                "            \"name\": \"YN\"\n" +
                "        },\n" +
                "        \"choices\": []\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 97,\n" +
                "        \"question\": \"كم عدد بنات الأبناء على قيد الحياة ؟\",\n" +
                "        \"question_en\": \"\",\n" +
                "        \"desc\": \"بنات الأبناء الذكور فقط.\",\n" +
                "        \"desc_en\": \"\",\n" +
                "        \"type_id\": 3,\n" +
                "        \"parent\": 72,\n" +
                "        \"pp\": 1,\n" +
                "        \"symbol\": \"Granddaughters\",\n" +
                "        \"group_id\": 1,\n" +
                "        \"default_answer\": 0,\n" +
                "        \"visible\": false,\n" +
                "        \"type\": {\n" +
                "            \"id\": 3,\n" +
                "            \"name\": \"NUMBER\"\n" +
                "        },\n" +
                "        \"choices\": []\n" +
                "    }\n" +
                "]";
        new Parser.ParseQuestionsTask(new JSONArray(demoString), new ConnectionDelegate() {

            @Override
            public void onConnectionSuccess(ArrayList<Question> questions, ArrayList<Answer> answers) {
                connectionDelegate.onConnectionSuccess(questions, null);
            }

            @Override
            public void onConnectionFailed(String code) {
                connectionDelegate.onConnectionFailed(ErrorCodes.BAD_REQUEST.getCode());

            }
        }).execute();

    }


    public static void submitAnswers(Context context, final ConnectionDelegate connectionDelegate, JsonObject jsonObject) {
        Log.d("submit", "request body: " + jsonObject);

        Ion.with(context)
                .load("POST", APIEndPoints.SUBMIT_API)
                .setHeader("Content-Type", "application/json")
                .addHeader("language", SharedPrefUtils.getLanguage())
                .setJsonObjectBody(jsonObject)
                .asString()
                .withResponse()
                .setCallback((e, result) -> {

                    if (result != null && result.getResult() != null)
                        Log.d("submit", "submit result: " + result.getResult());

                    if (e == null && result != null && result.getHeaders().code() == 200) {
                        try {

                            new Parser.ParseResultTask(new JSONArray(result.getResult()), new ConnectionDelegate() {

                                @Override
                                public void onConnectionSuccess(ArrayList<Question> questions, ArrayList<Answer> answers) {
                                    connectionDelegate.onConnectionSuccess(null, answers);
                                }

                                @Override
                                public void onConnectionFailed(String code) {
                                    connectionDelegate.onConnectionFailed(ErrorCodes.BAD_REQUEST.getCode());

                                }
                            }).execute();

                        } catch (JSONException e1) {
                            Log.d("submit", "JSONException: " + result.getResult());

                            e1.printStackTrace();
                            connectionDelegate.onConnectionFailed(ErrorCodes.BAD_REQUEST.getCode());

                        }
                    } else {

                        if (e != null) {
                            Log.d("submit", "exception thrown");
                            connectionDelegate.onConnectionFailed(ErrorCodes.BAD_REQUEST.code);

                        } else {
                            if (result != null && result.getHeaders() != null && result.getResult() != null) {
                                Log.d("submit", "headers: " + result.getHeaders());
                            }

                            if (result != null) {
                                String responseCode = result.getHeaders().code() + "";
                                if (responseCode.equals(ErrorCodes.BAD_REQUEST.code)) {

                                    try {
                                        JSONObject resultJson = new JSONObject(result.getResult());
                                        JSONObject errorJson = resultJson.optJSONObject("data").optJSONObject("error");
                                        StringBuilder resultText = new StringBuilder();

                                        Iterator<?> keys = errorJson.keys();

                                        while (keys.hasNext()) {
                                            String key = (String) keys.next();
                                            if (errorJson.get(key) instanceof JSONObject) {
                                                JSONObject errJson = errorJson.getJSONObject(key);
                                                resultText.append(key).append(" ").append(errJson.optString(key));
                                            }
                                        }

                                        connectionDelegate.onConnectionFailed(resultText.toString());

                                    } catch (JSONException je) {
                                        connectionDelegate.onConnectionFailed(responseCode);

                                    }
                                } else {
                                    connectionDelegate.onConnectionFailed(responseCode);

                                }
                            } else
                                connectionDelegate.onConnectionFailed(ErrorCodes.BAD_REQUEST.code);
                        }
                    }
                });
    }
}
