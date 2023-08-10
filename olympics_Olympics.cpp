#include "olympics_Olympics.h"
#include <iostream>
#include <vector>
#include <set>
#include <string>
#include <unordered_map>
#include <map>
#include <algorithm>

using namespace std;


JNIEXPORT jstring JNICALL Java_olympics_Olympics_first
(JNIEnv* env, jobject first, jstring filters, jstring fltr, jobjectArray cmp) {

	int length_competitor = env->GetArrayLength(cmp);

	vector<string> competitor;
	set<int> competitors;


	//filteri sa imenima
	const jsize str_length1 = env->GetStringUTFLength(filters);
	const char* characters1 = env->GetStringUTFChars(filters, (jboolean*)0);
	string filterss(characters1, str_length1);


	//filteri za niz
	const jsize str_length2 = env->GetStringUTFLength(fltr);
	const char* characters2 = env->GetStringUTFChars(fltr, (jboolean*)0);
	string filtr(characters2, str_length2);

	
	for (int i = 0; i < length_competitor; i++) {
		jstring jstr = (jstring)(env->GetObjectArrayElement(cmp, i));
		const jsize str_length = env->GetStringUTFLength(jstr);
		const char* characters = env->GetStringUTFChars(jstr, (jboolean*)0);
		string str(characters, str_length);

		competitor.push_back(str);

		env->ReleaseStringUTFChars(jstr, characters);
		env->DeleteLocalRef(jstr);
	}


	int* filtrs = new int[4];
	for (int i = 0; i < 4; i++) {
		filtrs[i] = 0;
	}

	//initialize filters

	int start = 0;
	string delimiter = "!";
	int end = filtr.find(delimiter);
	int num;

	while (end != -1) {
		num = stoi(filtr.substr(start, end - start));
		if ((num < 1) || (num > 4)); //throw Error
		start = end + delimiter.size();
		end = filtr.find(delimiter, start);
		filtrs[num - 1] = num;
	}
	num = stoi(filtr.substr(start, end - start));
	if ((num < 1) || (num > 4)); // throw Error
	filtrs[num - 1] = num;

	// uzmi imena iz filtera za imena

	start = 0;
	delimiter = "!";
	end = filterss.find(delimiter);

	string sport = filterss.substr(start, end - start);

	start = end + delimiter.size();
	end =filterss.find(delimiter, start);

	int year = stoi(filterss.substr(start, end - start));

	start = end + delimiter.size();
	end = filterss.find(delimiter, start);

	string medal = filterss.substr(start, end - start);

	start = end + delimiter.size();
	end = filterss.find(delimiter, start);

	string type = filterss.substr(start, end - start);

	map<string, set<int>> countries;

	//competitors
	for (int i = 0; i < length_competitor; i++) {

		int start = 0;
		string delimiter = "!";
		int end = competitor.at(i).find(delimiter);
		int integer;
		string str;

		str = competitor.at(i).substr(start, end - start);
		start = end + delimiter.size();
		end = competitor.at(i).find(delimiter, start);

		if (str == "I") {

			int index = stoi(competitor.at(i).substr(start, end - start));

			start = end + delimiter.size();
			end = competitor.at(i).find(delimiter,start);

			string sport1 = competitor.at(i).substr(start, end - start);

			start = end + delimiter.size();
			end = competitor.at(i).find(delimiter, start);

			string country1 = competitor.at(i).substr(start, end - start);

			start = end + delimiter.size();
			end = competitor.at(i).find(delimiter, start);

			int year1 = stoi(competitor.at(i).substr(start, end - start));

			start = end + delimiter.size();
			end = competitor.at(i).find(delimiter, start);

			string medal1 = competitor.at(i).substr(start, end - start);


			int flag = 0;
			if ((filtrs[0] ? (sport1 == sport) : 1) && 
				(filtrs[1] ? (year1 == year) : 1) && (filtrs[2] ? (str == type) : 1) && (filtrs[3] ? (medal1 == medal) : 1)) {

				flag++;
				competitors.insert(index);

			}

			if (flag) {
				if (countries.count(country1) == 0) {
					set<int> new_set;
					new_set.insert(index);
					countries.insert({ country1, new_set});
				}
				else
					countries.find(country1)->second.insert(index);
			}

		}
		else if (str == "T") {

			string index = competitor.at(i).substr(start, end - start);


			start = end + delimiter.size();
			end = competitor.at(i).find(delimiter, start);

			string sport1 = competitor.at(i).substr(start, end - start);

			start = end + delimiter.size();
			end = competitor.at(i).find(delimiter, start);

			string country1 = competitor.at(i).substr(start, end - start);

			start = end + delimiter.size();
			end = competitor.at(i).find(delimiter, start);

			int year1 = stoi(competitor.at(i).substr(start, end - start));

			start = end + delimiter.size();
			end = competitor.at(i).find(delimiter, start);

			string medal1 = competitor.at(i).substr(start, end - start);

			// razdvajanje indeksa

			vector<int> indices;

			int start1 = 0;
			string delimiter1 = ",";
			int end1 = index.find(delimiter1);
			int num;

			while (end1 != -1) {
				num = stoi(index.substr(start1, end1 - start1));
				start1 = end1 + delimiter1.size();
				end1 = index.find(delimiter1, start1);
				indices.push_back(num);
			}
			num = stoi(index.substr(start1, end1 - start1));
			indices.push_back(num);

			int flag = 0;

			for (int i = 0; i < indices.size(); i++) {
				if ((filtrs[0] ? (sport1 == sport) : 1) && 
					(filtrs[1] ? (year1 == year) : 1) && (filtrs[2] ? (str == type) : 1) && (filtrs[3] ? (medal1 == medal) : 1)) {
					competitors.insert(indices.at(i));
					flag++;
				}

				if (flag) {
					for (int i = 0; i < flag; i++) {
						if (countries.count(country1) == 0) {
							set<int> new_set;
							new_set.insert(indices.at(i));
							countries.insert({ country1, new_set });
						}
						else
							countries.find(country1)->second.insert(indices.at(i));
					}
				}

			}

		}

	}

	

	if (countries.size() == 0) {
		string ret = "";
		return env->NewStringUTF(ret.c_str());
	}

	if (countries.size() == 1) {
		string ret;
		ret.append(countries.begin()->first);
		ret.append(",");
		ret.append(to_string(countries.begin()->second.size()));
		return env->NewStringUTF(ret.c_str());
	}

	vector<pair<string, int>> vec;

	map<string, set<int>> ::iterator iter;

	for (iter = countries.begin(); iter != countries.end(); iter++) {
		vec.push_back(make_pair(iter->first, iter->second.size()));
	}

	sort(vec.begin(), vec.end(), [&](pair<string, int> p1, pair<string, int> p2) {
		return p1.second > p2.second;
		});

	vector<pair<string, int>> vec1;
	int sum = 0;

	if (countries.size() >= 10) {

		for (int i = 9; i < vec.size(); i++) {
			sum += vec.at(i).second;
		}

	}

	for (int i = 0; i < countries.size(); i++) {
		vec1.push_back(vec.at(i));
	}

	if(countries.size() >= 10 ) vec1.push_back(make_pair("Others", sum));


	sort(vec1.begin(), vec1.end(), [&](pair<string, int> p1, pair<string, int> p2) {
		return p1.second > p2.second;
		});

	string result = "";

	int finish = 10;
	if (vec1.size() < 10) finish = vec1.size();


	for (int i = 0; i < finish; i++) {
		result.append(vec1.at(i).first);

		result.append(",");

		result.append(to_string(vec1.at(i).second));

		if (i != finish-1) result.append("!");
	}

	return env->NewStringUTF(result.c_str());

}

JNIEXPORT jstring JNICALL Java_olympics_Olympics_second
(JNIEnv* env, jobject second, jobjectArray cmp, jint s_year, jint e_year, jstring szn) {

	int length_competitor = env->GetArrayLength(cmp);

	int start_year = (int)s_year;
	int end_year = (int)e_year;

	vector<string> competitor;
	set<string> competitors;

	jboolean bln;
	const char* s = env->GetStringUTFChars(szn, &bln);
	string season_check = s;


	for (int i = 0; i < length_competitor; i++) {
		jstring jstr = (jstring)(env->GetObjectArrayElement(cmp, i));
		const jsize str_length = env->GetStringUTFLength(jstr);
		const char* characters = env->GetStringUTFChars(jstr, (jboolean*)0);
		string str(characters, str_length);

		competitor.push_back(str);

		env->ReleaseStringUTFChars(jstr, characters);
		env->DeleteLocalRef(jstr);
	}

	map<int, set<string>> years;

	//competitors
	for (int i = 0; i < length_competitor; i++) {

		int start = 0;
		string delimiter = "!";
		int end = competitor.at(i).find(delimiter);

		int year1 = stoi(competitor.at(i).substr(start, end - start));

		if ((year1 < start_year) || (year1 > end_year)) continue;

		start = end + delimiter.size();
		end = competitor.at(i).find(delimiter, start);

		string discipline = competitor.at(i).substr(start, end - start);
		
		start = end + delimiter.size();
		end = competitor.at(i).find(delimiter, start);

		string season = competitor.at(i).substr(start, end - start);

		if (season != season_check) continue;

		if (years.count(year1) == 0) {

			set<string> new_set;
			new_set.insert(discipline);
			years.insert({ year1, new_set });
		}
		else {

			years.find(year1)->second.insert(discipline);
		}

	}

	vector<pair<int, int>> vec;

	map<int, set<string>> ::iterator iter;

	for (iter = years.begin(); iter != years.end(); iter++) {
		vec.push_back(make_pair(iter->first, iter->second.size()));
	}

	sort(vec.begin(), vec.end(), [&](pair<int, int> p1, pair<int, int> p2) {
		return p1.first < p2.first;
		});

	string result;



	for (int i = 0; i < vec.size(); i++) {

		result.append(to_string(vec.at(i).first));

		result.append(",");

		result.append(to_string(vec.at(i).second));

		if (i != (vec.size() - 1)) result.append("!");
	}

	return env->NewStringUTF(result.c_str());

	/*int length_competitor = env->GetArrayLength(cmp);

	int start_year = (int)s_year;
	int end_year = (int)e_year;

	vector<string> competitor;
	set<string> competitors;

	jboolean bln;
	const char* s = env->GetStringUTFChars(szn, &bln);
	string season_check = s;


	//filteri sa imenima
	const jsize str_length1 = env->GetStringUTFLength(filters);
	const char* characters1 = env->GetStringUTFChars(filters, (jboolean*)0);
	string filterss(characters1, str_length1);


	//filteri za niz
	const jsize str_length2 = env->GetStringUTFLength(fltr);
	const char* characters2 = env->GetStringUTFChars(fltr, (jboolean*)0);
	string filtr(characters2, str_length2);


	for (int i = 0; i < length_competitor; i++) {
		jstring jstr = (jstring)(env->GetObjectArrayElement(cmp, i));
		const jsize str_length = env->GetStringUTFLength(jstr);
		const char* characters = env->GetStringUTFChars(jstr, (jboolean*)0);
		string str(characters, str_length);

		competitor.push_back(str);

		env->ReleaseStringUTFChars(jstr, characters);
		env->DeleteLocalRef(jstr);
	}


	int* filtrs = new int[4];
	for (int i = 0; i < 4; i++) {
		filtrs[i] = 0;
	}

	//initialize filters

	int start = 0;
	string delimiter = "!";
	int end = filtr.find(delimiter);
	int num;

	while (end != -1) {
		num = stoi(filtr.substr(start, end - start));
		if ((num < 1) || (num > 5)); //throw Error
		start = end + delimiter.size();
		end = filtr.find(delimiter, start);
		filtrs[num - 1] = num;
	}
	num = stoi(filtr.substr(start, end - start));
	if ((num < 1) || (num > 5)); // throw Error
	filtrs[num - 1] = num;

	// uzmi imena iz filtera za imena

	start = 0;
	delimiter = "!";
	end = filterss.find(delimiter);

	string sport = filterss.substr(start, end - start);

	start = end + delimiter.size();
	end = filterss.find(delimiter, start);

	int year = stoi(filterss.substr(start, end - start));

	start = end + delimiter.size();
	end = filterss.find(delimiter, start);

	string medal = filterss.substr(start, end - start);

	start = end + delimiter.size();
	end = filterss.find(delimiter, start);

	string type = filterss.substr(start, end - start);

	map<int, set<string>> years;

	//competitors
	for (int i = 0; i < length_competitor; i++) {

		int start = 0;
		string delimiter = "!";
		int end = competitor.at(i).find(delimiter);

		string sport1 = competitor.at(i).substr(start, end - start);


		start = end + delimiter.size();
		end = competitor.at(i).find(delimiter, start);

		string country1 = competitor.at(i).substr(start, end - start);

		start = end + delimiter.size();
		end = competitor.at(i).find(delimiter, start);

		int year1 = stoi(competitor.at(i).substr(start, end - start));

		if ((year1 < start_year) || (year1 > end_year)) continue;


		start = end + delimiter.size();
		end = competitor.at(i).find(delimiter, start);

		string medal1 = competitor.at(i).substr(start, end - start);

		start = end + delimiter.size();
		end = competitor.at(i).find(delimiter, start);

		string type1 = competitor.at(i).substr(start, end - start);

		start = end + delimiter.size();
		end = competitor.at(i).find(delimiter, start);

		string discipline = competitor.at(i).substr(start, end - start);
		
		start = end + delimiter.size();
		end = competitor.at(i).find(delimiter, start);

		string season = competitor.at(i).substr(start, end - start);

		if (season != season_check) continue;

		if (years.count(year1) == 0) {

			set<string> new_set;
			new_set.insert(discipline);
			years.insert({ year1, new_set });
		}
		else {

			years.find(year1)->second.insert(discipline);
		}

		continue;

		int flag = 0;

		if ((filtrs[0] ? (sport1 == sport) : 1) && 
			(filtrs[2] ? (year1 == year) : 1) && (filtrs[3] ? (type1 == type) : 1) && (filtrs[4] ? (medal1 == medal) : 1)) {
			//flag++;

			
			if (years.count(year1) == 0) {
				
				set<string> new_set;
				new_set.insert(discipline);
				years.insert({ year1, new_set });
			}
			else {
				
				years.find(year1)->second.insert(discipline);
			}

		}
		if (flag) {
			
		}


	}

	vector<pair<int, int>> vec;

	map<int, set<string>> ::iterator iter;

	for (iter = years.begin(); iter != years.end(); iter++) {
		vec.push_back(make_pair(iter->first, iter->second.size()));
	}

	sort(vec.begin(), vec.end(), [&](pair<int, int> p1, pair<int, int> p2) {
		return p1.first < p2.first;
		});

	string result;



	for (int i = 0; i < vec.size(); i++) {

		result.append(to_string(vec.at(i).first));

		result.append(",");

		result.append(to_string(vec.at(i).second));

		if (i != (vec.size() - 1)) result.append("!");
	}

	return env->NewStringUTF(result.c_str());*/

}

JNIEXPORT jstring JNICALL Java_olympics_Olympics_third
(JNIEnv* env, jobject third, jobjectArray cmp, jint s_year, jint e_year ,jstring szn) {

	int length_competitor = env->GetArrayLength(cmp);

	vector<string> competitor;
	set<int>competitors;

	int start_year = (int)s_year;
	int end_year = (int)e_year;

	jboolean bln;
	const char* s = env->GetStringUTFChars(szn, &bln);
	string season_check = s;


	for (int i = 0; i < length_competitor; i++) {
		jstring jstr = (jstring)(env->GetObjectArrayElement(cmp, i));
		const jsize str_length = env->GetStringUTFLength(jstr);
		const char* characters = env->GetStringUTFChars(jstr, (jboolean*)0);
		string str(characters, str_length);

		competitor.push_back(str);

		env->ReleaseStringUTFChars(jstr, characters);
		env->DeleteLocalRef(jstr);
	}

	
	//make struct to use for hash function for unordered_map
	struct Pair {
		int sum;
		double avg;

		Pair(double a) {
			sum = 1;
			avg = a;
		}

		bool operator==(const Pair& p) const {
			return sum == p.sum && avg == p.avg;
		}
	};

	//hash function for unordered_map
	class Hash {
	public:
		size_t operator()(const Pair& p) const {
			return (p.avg / 3) + p.sum;
		}
	};

	unordered_map<int, Pair, Hash> hmap;
	map<int, set<int>> hset;

	for (int i = 0; i < length_competitor; i++) {

		int start = 0;
		string delimiter = "!";
		int end = competitor.at(i).find(delimiter);
		int integer;
		string str;

		str = competitor.at(i).substr(start, end - start);
		start = end + delimiter.size();
		end = competitor.at(i).find(delimiter, start);

		if (str == "I") {

			int index = stoi(competitor.at(i).substr(start, end - start));

			start = end + delimiter.size();
			end = competitor.at(i).find(delimiter, start);

			int year1 = stoi(competitor.at(i).substr(start, end - start));

			if ((year1 < start_year) || (year1 > end_year)) continue;

			start = end + delimiter.size();
			end = competitor.at(i).find(delimiter, start);

			double height = stof(competitor.at(i).substr(start, end - start));

			start = end + delimiter.size();
			end = competitor.at(i).find(delimiter, start);

			string season = competitor.at(i).substr(start, end - start);

			if (season != season_check) continue;

			if (height > 0) {
				if (hset.count(year1) == 0) {
					set<int> new_set;
					new_set.insert(index);
					hset.insert({ year1, new_set });
					hmap.insert({ year1, height });
				}
				else {
					if (hset.find(year1)->second.count(index) == 0) {
						hmap.find(year1)->second.avg += height;
						hmap.find(year1)->second.sum++;
					}

				}

			}

		}
		else if (str == "T") {

			string index = competitor.at(i).substr(start, end - start);

			start = end + delimiter.size();
			end = competitor.at(i).find(delimiter, start);

			int year1 = stoi(competitor.at(i).substr(start, end - start));

			if ((year1 < start_year) || (year1 > end_year)) continue;

			start = end + delimiter.size();
			end = competitor.at(i).find(delimiter, start);

			string hheight = competitor.at(i).substr(start, end - start);

			start = end + delimiter.size();
			end = competitor.at(i).find(delimiter, start);

			string season = competitor.at(i).substr(start, end - start);

			if (season != season_check) continue;

			// razdvajanje indeksa

			vector<int> indices;

			int start1 = 0;
			string delimiter1 = ",";
			int end1 = index.find(delimiter1);
			int num;

			while (end1 != -1) {
				num = stoi(index.substr(start1, end1 - start1));
				start1 = end1 + delimiter1.size();
				end1 = index.find(delimiter1, start1);
				indices.push_back(num);
			}
			num = stoi(index.substr(start1, end1 - start1));
			indices.push_back(num);

			// razdvajanje visina

			vector<double> heights;

			int start2 = 0;
			string delimiter2 = ",";
			int end2 = hheight.find(delimiter2);
			double num2;

			while (end2 != -1) {
				num2 = stof(hheight.substr(start2, end2 - start2));
				start2 = end2 + delimiter2.size();
				end2 = hheight.find(delimiter2, start2);
				heights.push_back(num2);
			}
			num2 = stof(hheight.substr(start2, end2 - start2));
			heights.push_back(num2);

			for (int i = 0; i < indices.size(); i++) {
				if (heights.at(i) > 0) {
					if (hset.count(year1) == 0) {
						set<int> new_set;
						new_set.insert(indices.at(i));
						hset.insert({ year1, new_set });
						hmap.insert({ year1, heights.at(i) });
					}
					else {
						if (hset.find(year1)->second.count(indices.at(i)) == 0) {
							hmap.find(year1)->second.avg += heights.at(i);
							hmap.find(year1)->second.sum++;
						}

					}
				}
			}
		}

	}

	vector<pair<int, double>> vec;

	unordered_map<int, Pair, Hash> ::iterator iter;

	for (iter = hmap.begin(); iter != hmap.end(); iter++) {
		vec.push_back(make_pair(iter->first, (1.0*iter->second.avg)/iter->second.sum));
	}

	sort(vec.begin(), vec.end(), [&](pair<int, int> p1, pair<int, int> p2) {
		return p1.first < p2.first;
		});

	string result;

	for (int i = 0; i < vec.size(); i++) {

		result.append(to_string(vec.at(i).first));

		result.append(",");

		result.append(to_string(vec.at(i).second));

		if (i != (vec.size() - 1)) result.append("!");
	}

	return env->NewStringUTF(result.c_str());

	/*
	int length_competitor = env->GetArrayLength(cmp);

	vector<string> competitor;
	set<int>competitors;

	int start_year = (int)s_year;
	int end_year = (int)e_year;

	jboolean bln;
	const char* s = env->GetStringUTFChars(szn, &bln);
	string season_check = s;

	//filteri sa imenima
	const jsize str_length1 = env->GetStringUTFLength(filters);
	const char* characters1 = env->GetStringUTFChars(filters, (jboolean*)0);
	string filterss(characters1, str_length1);


	//filteri za niz
	const jsize str_length2 = env->GetStringUTFLength(fltr);
	const char* characters2 = env->GetStringUTFChars(fltr, (jboolean*)0);
	string filtr(characters2, str_length2);


	for (int i = 0; i < length_competitor; i++) {
		jstring jstr = (jstring)(env->GetObjectArrayElement(cmp, i));
		const jsize str_length = env->GetStringUTFLength(jstr);
		const char* characters = env->GetStringUTFChars(jstr, (jboolean*)0);
		string str(characters, str_length);

		competitor.push_back(str);

		env->ReleaseStringUTFChars(jstr, characters);
		env->DeleteLocalRef(jstr);
	}


	int* filtrs = new int[5];
	for (int i = 0; i < 5; i++) {
		filtrs[i] = 0;
	}

	//initialize filters

	int start = 0;
	string delimiter = "!";
	int end = filtr.find(delimiter);
	int num;

	while (end != -1) {
		num = stoi(filtr.substr(start, end - start));
		if ((num < 1) || (num > 5)); //throw Error
		start = end + delimiter.size();
		end = filtr.find(delimiter, start);
		filtrs[num - 1] = num;
	}
	num = stoi(filtr.substr(start, end - start));
	if ((num < 1) || (num > 5)); // throw Error
	filtrs[num - 1] = num;

	// uzmi imena iz filtera za imena

	start = 0;
	delimiter = "!";
	end = filterss.find(delimiter);

	string sport = filterss.substr(start, end - start);

	start = end + delimiter.size();
	end = filterss.find(delimiter, start);

	int year = stoi(filterss.substr(start, end - start));

	start = end + delimiter.size();
	end = filterss.find(delimiter, start);

	string medal = filterss.substr(start, end - start);

	start = end + delimiter.size();
	end = filterss.find(delimiter, start);

	string type = filterss.substr(start, end - start);

	double avg = 0;
	int sum = 0;

	
	//make struct to use for hash function for unordered_map
	struct Pair {
		int sum;
		double avg;

		Pair(double a) {
			sum = 1;
			avg = a;
		}

		bool operator==(const Pair& p) const {
			return sum == p.sum && avg == p.avg;
		}
	};

	//hash function for unordered_map
	class Hash {
	public:
		size_t operator()(const Pair& p) const {
			return (p.avg / 3) + p.sum;
		}
	};

	unordered_map<int, Pair, Hash> hmap;
	map<int, set<int>> hset;

	for (int i = 0; i < length_competitor; i++) {

		int start = 0;
		string delimiter = "!";
		int end = competitor.at(i).find(delimiter);
		int integer;
		string str;

		str = competitor.at(i).substr(start, end - start);
		start = end + delimiter.size();
		end = competitor.at(i).find(delimiter, start);

		if (str == "I") {

			int index = stoi(competitor.at(i).substr(start, end - start));

			start = end + delimiter.size();
			end = competitor.at(i).find(delimiter, start);

			string sport1 = competitor.at(i).substr(start, end - start);

			start = end + delimiter.size();
			end = competitor.at(i).find(delimiter, start);

			string country1 = competitor.at(i).substr(start, end - start);

			start = end + delimiter.size();
			end = competitor.at(i).find(delimiter, start);

			int year1 = stoi(competitor.at(i).substr(start, end - start));

			if ((year1 < start_year) || (year1 > end_year)) continue;

			start = end + delimiter.size();
			end = competitor.at(i).find(delimiter, start);

			string medal1 = competitor.at(i).substr(start, end - start);

			start = end + delimiter.size();
			end = competitor.at(i).find(delimiter, start);

			double height = stof(competitor.at(i).substr(start, end - start));

			start = end + delimiter.size();
			end = competitor.at(i).find(delimiter, start);

			string season = competitor.at(i).substr(start, end - start);

			if (season != season_check) continue;

			if ((filtrs[0] ? (sport1 == sport) : 1) &&
				(filtrs[2] ? (year1 == year) : 1) && (filtrs[3] ? (str == type) : 1) && (filtrs[4] ? (medal1 == medal) : 1)) {
				if (height > 0) {
					if (hset.count(year1) == 0) {
						set<int> new_set;
						new_set.insert(index);
						hset.insert({ year1, new_set });
						hmap.insert({ year1, height });
					}
					else {
						if (hset.find(year1)->second.count(index) == 0) {
							hmap.find(year1)->second.avg += height;
							hmap.find(year1)->second.sum++;
						}
						
					}
					
				}
			}

		}
		else if (str == "T") {

			string index = competitor.at(i).substr(start, end - start);

			start = end + delimiter.size();
			end = competitor.at(i).find(delimiter, start);

			string sport1 = competitor.at(i).substr(start, end - start);

			start = end + delimiter.size();
			end = competitor.at(i).find(delimiter, start);

			string country1 = competitor.at(i).substr(start, end - start);

			start = end + delimiter.size();
			end = competitor.at(i).find(delimiter, start);

			int year1 = stoi(competitor.at(i).substr(start, end - start));

			if ((year1 < start_year) || (year1 > end_year)) continue;

			start = end + delimiter.size();
			end = competitor.at(i).find(delimiter, start);

			string medal1 = competitor.at(i).substr(start, end - start);

			start = end + delimiter.size();
			end = competitor.at(i).find(delimiter, start);

			string hheight = competitor.at(i).substr(start, end - start);

			start = end + delimiter.size();
			end = competitor.at(i).find(delimiter, start);

			string season = competitor.at(i).substr(start, end - start);

			if (season != season_check) continue;

			// razdvajanje indeksa

			vector<int> indices;

			int start1 = 0;
			string delimiter1 = ",";
			int end1 = index.find(delimiter1);
			int num;

			while (end1 != -1) {
				num = stoi(index.substr(start1, end1 - start1));
				start1 = end1 + delimiter1.size();
				end1 = index.find(delimiter1, start1);
				indices.push_back(num);
			}
			num = stoi(index.substr(start1, end1 - start1));
			indices.push_back(num);

			// razdvajanje visina

			vector<double> heights;

			int start2 = 0;
			string delimiter2 = ",";
			int end2 = hheight.find(delimiter2);
			double num2;

			while (end2 != -1) {
				num2 = stof(hheight.substr(start2, end2 - start2));
				start2 = end2 + delimiter2.size();
				end2 = hheight.find(delimiter2, start2);
				heights.push_back(num2);
			}
			num2 = stof(hheight.substr(start2, end2 - start2));
			heights.push_back(num2);

			for (int i = 0; i < indices.size(); i++) {
				if ((filtrs[0] ? (sport1 == sport) : 1) && 
					(filtrs[2] ? (year1 == year) : 1) && (filtrs[3] ? (str == type) : 1) && (filtrs[4] ? (medal1 == medal) : 1)) {
					if (heights.at(i) > 0) {
						if (hset.count(year1) == 0) {
							set<int> new_set;
							new_set.insert(indices.at(i));
							hset.insert({ year1, new_set });
							hmap.insert({ year1, heights.at(i)});
						}
						else {
							if (hset.find(year1)->second.count(indices.at(i)) == 0) {
								hmap.find(year1)->second.avg += heights.at(i);
								hmap.find(year1)->second.sum++;
							}

						}
					}
				}
			}
		}

	}

	vector<pair<int, double>> vec;

	unordered_map<int, Pair, Hash> ::iterator iter;

	for (iter = hmap.begin(); iter != hmap.end(); iter++) {
		vec.push_back(make_pair(iter->first, (1.0*iter->second.avg)/iter->second.sum));
	}

	sort(vec.begin(), vec.end(), [&](pair<int, int> p1, pair<int, int> p2) {
		return p1.first < p2.first;
		});

	string result;

	for (int i = 0; i < vec.size(); i++) {

		result.append(to_string(vec.at(i).first));

		result.append(",");

		result.append(to_string(vec.at(i).second));

		if (i != (vec.size() - 1)) result.append("!");
	}

	return env->NewStringUTF(result.c_str());
	*/


}

JNIEXPORT jstring JNICALL Java_olympics_Olympics_fourth
(JNIEnv* env, jobject fourth, jobjectArray cmp, jint s_year, jint e_year,jstring szn) {


	int length_competitor = env->GetArrayLength(cmp);

	vector<string> competitor;
	set<int>competitors;

	int start_year = (int)s_year;
	int end_year = (int)e_year;

	jboolean bln;
	const char* s = env->GetStringUTFChars(szn, &bln);
	string season_check = s;


	for (int i = 0; i < length_competitor; i++) {
		jstring jstr = (jstring)(env->GetObjectArrayElement(cmp, i));
		const jsize str_length = env->GetStringUTFLength(jstr);
		const char* characters = env->GetStringUTFChars(jstr, (jboolean*)0);
		string str(characters, str_length);

		competitor.push_back(str);

		env->ReleaseStringUTFChars(jstr, characters);
		env->DeleteLocalRef(jstr);
	}


	//make struct to use for hash function for unordered_map
	struct Pair {
		int sum;
		double avg;

		Pair(double a) {
			sum = 1;
			avg = a;
		}

		bool operator==(const Pair& p) const {
			return sum == p.sum && avg == p.avg;
		}
	};

	//hash function for unordered_map
	class Hash {
	public:
		size_t operator()(const Pair& p) const {
			return (p.avg / 3) + p.sum;
		}
	};

	unordered_map<int, Pair, Hash> hmap;
	map<int, set<int>> hset;


	for (int i = 0; i < length_competitor; i++) {

		int start = 0;
		string delimiter = "!";
		int end = competitor.at(i).find(delimiter);
		int integer;
		string str;

		str = competitor.at(i).substr(start, end - start);
		start = end + delimiter.size();
		end = competitor.at(i).find(delimiter, start);

		if (str == "I") {

			int index = stoi(competitor.at(i).substr(start, end - start));

			start = end + delimiter.size();
			end = competitor.at(i).find(delimiter, start);

			int year1 = stoi(competitor.at(i).substr(start, end - start));

			if ((year1 < start_year) || (year1 > end_year)) continue;

			start = end + delimiter.size();
			end = competitor.at(i).find(delimiter, start);

			double weight = stof(competitor.at(i).substr(start, end - start));

			start = end + delimiter.size();
			end = competitor.at(i).find(delimiter, start);

			string season = competitor.at(i).substr(start, end - start);

			if (season != season_check) continue;

			if (weight > 0) {
				if (hset.count(year1) == 0) {
					set<int> new_set;
					new_set.insert(index);
					hset.insert({ year1, new_set });
					hmap.insert({ year1, weight });
				}
				else {
					if (hset.find(year1)->second.count(index) == 0) {
						hmap.find(year1)->second.avg += weight;
						hmap.find(year1)->second.sum++;
					}

				}

			}

		}
		else if (str == "T") {

			string index = competitor.at(i).substr(start, end - start);

			start = end + delimiter.size();
			end = competitor.at(i).find(delimiter, start);

			int year1 = stoi(competitor.at(i).substr(start, end - start));

			if ((year1 < start_year) || (year1 > end_year)) continue;

			start = end + delimiter.size();
			end = competitor.at(i).find(delimiter, start);

			string wweight = competitor.at(i).substr(start, end - start);

			start = end + delimiter.size();
			end = competitor.at(i).find(delimiter, start);

			string season = competitor.at(i).substr(start, end - start);

			if (season != season_check) continue;

			// razdvajanje indeksa

			vector<int> indices;

			int start1 = 0;
			string delimiter1 = ",";
			int end1 = index.find(delimiter1);
			int num;

			while (end1 != -1) {
				num = stoi(index.substr(start1, end1 - start1));
				start1 = end1 + delimiter1.size();
				end1 = index.find(delimiter1, start1);
				indices.push_back(num);
			}
			num = stoi(index.substr(start1, end1 - start1));
			indices.push_back(num);

			// razdvajanje visina

			vector<double> weights;

			int start2 = 0;
			string delimiter2 = ",";
			int end2 = wweight.find(delimiter2);
			double num2;

			while (end2 != -1) {
				num2 = stof(wweight.substr(start2, end2 - start2));
				start2 = end2 + delimiter2.size();
				end2 = wweight.find(delimiter2, start2);
				weights.push_back(num2);
			}
			num2 = stof(wweight.substr(start2, end2 - start2));
			weights.push_back(num2);

			for (int i = 0; i < indices.size(); i++) {
				if (weights.at(i) > 0) {
					if (hset.count(year1) == 0) {
						set<int> new_set;
						new_set.insert(indices.at(i));
						hset.insert({ year1, new_set });
						hmap.insert({ year1, weights.at(i) });
					}
					else {
						if (hset.find(year1)->second.count(indices.at(i)) == 0) {
							hmap.find(year1)->second.avg += weights.at(i);
							hmap.find(year1)->second.sum++;
						}

					}
				}
			}
		}

	}

	vector<pair<int, double>> vec;

	unordered_map<int, Pair, Hash> ::iterator iter;

	for (iter = hmap.begin(); iter != hmap.end(); iter++) {
		vec.push_back(make_pair(iter->first, (1.0 * iter->second.avg) / iter->second.sum));
	}

	sort(vec.begin(), vec.end(), [&](pair<int, int> p1, pair<int, int> p2) {
		return p1.first < p2.first;
		});

	string result;

	for (int i = 0; i < vec.size(); i++) {

		result.append(to_string(vec.at(i).first));

		result.append(",");

		result.append(to_string(vec.at(i).second));

		if (i != (vec.size() - 1)) result.append("!");
	}

	return env->NewStringUTF(result.c_str());

	/*
	
	int length_competitor = env->GetArrayLength(cmp);

	vector<string> competitor;
	set<int>competitors;

	int start_year = (int)s_year;
	int end_year = (int)e_year;

	jboolean bln;
	const char* s = env->GetStringUTFChars(szn, &bln);
	string season_check = s;

	//filteri sa imenima
	const jsize str_length1 = env->GetStringUTFLength(filters);
	const char* characters1 = env->GetStringUTFChars(filters, (jboolean*)0);
	string filterss(characters1, str_length1);


	//filteri za niz
	const jsize str_length2 = env->GetStringUTFLength(fltr);
	const char* characters2 = env->GetStringUTFChars(fltr, (jboolean*)0);
	string filtr(characters2, str_length2);


	for (int i = 0; i < length_competitor; i++) {
		jstring jstr = (jstring)(env->GetObjectArrayElement(cmp, i));
		const jsize str_length = env->GetStringUTFLength(jstr);
		const char* characters = env->GetStringUTFChars(jstr, (jboolean*)0);
		string str(characters, str_length);

		competitor.push_back(str);

		env->ReleaseStringUTFChars(jstr, characters);
		env->DeleteLocalRef(jstr);
	}


	int* filtrs = new int[5];
	for (int i = 0; i < 5; i++) {
		filtrs[i] = 0;
	}

	//initialize filters

	int start = 0;
	string delimiter = "!";
	int end = filtr.find(delimiter);
	int num;

	while (end != -1) {
		num = stoi(filtr.substr(start, end - start));
		if ((num < 1) || (num > 5)); //throw Error
		start = end + delimiter.size();
		end = filtr.find(delimiter, start);
		filtrs[num - 1] = num;
	}
	num = stoi(filtr.substr(start, end - start));
	if ((num < 1) || (num > 5)); // throw Error
	filtrs[num - 1] = num;

	// uzmi imena iz filtera za imena

	start = 0;
	delimiter = "!";
	end = filterss.find(delimiter);

	string sport = filterss.substr(start, end - start);

	start = end + delimiter.size();
	end = filterss.find(delimiter, start);

	int year = stoi(filterss.substr(start, end - start));

	start = end + delimiter.size();
	end = filterss.find(delimiter, start);

	string medal = filterss.substr(start, end - start);

	start = end + delimiter.size();
	end = filterss.find(delimiter, start);

	string type = filterss.substr(start, end - start);

	double avg = 0;
	int sum = 0;



	//make struct to use for hash function for unordered_map
	struct Pair {
		int sum;
		double avg;

		Pair(double a) {
			sum = 1;
			avg = a;
		}

		bool operator==(const Pair& p) const {
			return sum == p.sum && avg == p.avg;
		}
	};

	//hash function for unordered_map
	class Hash {
	public:
		size_t operator()(const Pair& p) const {
			return (p.avg / 3) + p.sum;
		}
	};

	unordered_map<int, Pair, Hash> hmap;
	map<int, set<int>> hset;


	for (int i = 0; i < length_competitor; i++) {

		int start = 0;
		string delimiter = "!";
		int end = competitor.at(i).find(delimiter);
		int integer;
		string str;

		str = competitor.at(i).substr(start, end - start);
		start = end + delimiter.size();
		end = competitor.at(i).find(delimiter, start);

		if (str == "I") {

			int index = stoi(competitor.at(i).substr(start, end - start));

			start = end + delimiter.size();
			end = competitor.at(i).find(delimiter, start);

			string sport1 = competitor.at(i).substr(start, end - start);

			start = end + delimiter.size();
			end = competitor.at(i).find(delimiter, start);

			string country1 = competitor.at(i).substr(start, end - start);

			start = end + delimiter.size();
			end = competitor.at(i).find(delimiter, start);

			int year1 = stoi(competitor.at(i).substr(start, end - start));

			if ((year1 < start_year) || (year1 > end_year)) continue;

			start = end + delimiter.size();
			end = competitor.at(i).find(delimiter, start);

			string medal1 = competitor.at(i).substr(start, end - start);

			start = end + delimiter.size();
			end = competitor.at(i).find(delimiter, start);

			double weight = stof(competitor.at(i).substr(start, end - start));

			start = end + delimiter.size();
			end = competitor.at(i).find(delimiter, start);

			string season = competitor.at(i).substr(start, end - start);

			if (season != season_check) continue;

			if ((filtrs[0] ? (sport1 == sport) : 1) && 
				(filtrs[2] ? (year1 == year) : 1) && (filtrs[3] ? (str == type) : 1) && (filtrs[4] ? (medal1 == medal) : 1)) {
				if (weight > 0) {
					if (hset.count(year1) == 0) {
						set<int> new_set;
						new_set.insert(index);
						hset.insert({ year1, new_set });
						hmap.insert({ year1, weight });
					}
					else {
						if (hset.find(year1)->second.count(index) == 0) {
							hmap.find(year1)->second.avg += weight;
							hmap.find(year1)->second.sum++;
						}

					}

				}
			}

		}
		else if (str == "T") {

			string index = competitor.at(i).substr(start, end - start);

			start = end + delimiter.size();
			end = competitor.at(i).find(delimiter, start);

			string sport1 = competitor.at(i).substr(start, end - start);

			start = end + delimiter.size();
			end = competitor.at(i).find(delimiter, start);

			string country1 = competitor.at(i).substr(start, end - start);

			start = end + delimiter.size();
			end = competitor.at(i).find(delimiter, start);

			int year1 = stoi(competitor.at(i).substr(start, end - start));

			if ((year1 < start_year) || (year1 > end_year)) continue;

			start = end + delimiter.size();
			end = competitor.at(i).find(delimiter, start);

			string medal1 = competitor.at(i).substr(start, end - start);

			start = end + delimiter.size();
			end = competitor.at(i).find(delimiter, start);

			string wweight = competitor.at(i).substr(start, end - start);

			start = end + delimiter.size();
			end = competitor.at(i).find(delimiter, start);

			string season = competitor.at(i).substr(start, end - start);

			if (season != season_check) continue;

			// razdvajanje indeksa

			vector<int> indices;

			int start1 = 0;
			string delimiter1 = ",";
			int end1 = index.find(delimiter1);
			int num;

			while (end1 != -1) {
				num = stoi(index.substr(start1, end1 - start1));
				start1 = end1 + delimiter1.size();
				end1 = index.find(delimiter1, start1);
				indices.push_back(num);
			}
			num = stoi(index.substr(start1, end1 - start1));
			indices.push_back(num);

			// razdvajanje visina

			vector<double> weights;

			int start2 = 0;
			string delimiter2 = ",";
			int end2 = wweight.find(delimiter2);
			double num2;

			while (end2 != -1) {
				num2 = stof(wweight.substr(start2, end2 - start2));
				start2 = end2 + delimiter2.size();
				end2 = wweight.find(delimiter2, start2);
				weights.push_back(num2);
			}
			num2 = stof(wweight.substr(start2, end2 - start2));
			weights.push_back(num2);

			for (int i = 0; i < indices.size(); i++) {
				if ((filtrs[0] ? (sport1 == sport) : 1) && 
					(filtrs[2] ? (year1 == year) : 1) && (filtrs[3] ? (str == type) : 1) && (filtrs[4] ? (medal1 == medal) : 1)) {
					if (weights.at(i) > 0) {
						if (hset.count(year1) == 0) {
							set<int> new_set;
							new_set.insert(indices.at(i));
							hset.insert({ year1, new_set });
							hmap.insert({ year1, weights.at(i) });
						}
						else {
							if (hset.find(year1)->second.count(indices.at(i)) == 0) {
								hmap.find(year1)->second.avg += weights.at(i);
								hmap.find(year1)->second.sum++;
							}

						}
					}
				}
			}
		}

	}

	vector<pair<int, double>> vec;

	unordered_map<int, Pair, Hash> ::iterator iter;

	for (iter = hmap.begin(); iter != hmap.end(); iter++) {
		vec.push_back(make_pair(iter->first, (1.0 * iter->second.avg) / iter->second.sum));
	}

	sort(vec.begin(), vec.end(), [&](pair<int, int> p1, pair<int, int> p2) {
		return p1.first < p2.first;
		});

	string result;

	for (int i = 0; i < vec.size(); i++) {

		result.append(to_string(vec.at(i).first));

		result.append(",");

		result.append(to_string(vec.at(i).second));

		if (i != (vec.size() - 1)) result.append("!");
	}

	return env->NewStringUTF(result.c_str());
	*/

	
}