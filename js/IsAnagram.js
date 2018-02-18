// Generates Frequency Tables (Hashmaps) to determine whether 2 words have the same letters in the same frequency (anagrams)

function isAnagram(a,b){
	if (a.length != b.length)
		return false;
	
	let a_tab = createFreqTable(a);
	let b_tab = createFreqTable(b);
	
	return compareFreqTables(a_tab,b_tab);
		
}

function createFreqTable(t){
	let ret = {};
	for (let x of t){
		if(typeof(ret[x]) == 'undefined')
			ret[x] = 1;
		else
			ret[x]++;
	}
	return ret;
}

function compareFreqTables(a,b){
	if(keys(a).length != keys(b).length) //Different number of character classes
		return false;
	for (let x in a){
		if(typeof(b[x]) == 'undefined') //b does not contain an entry that a has
			return false;
		if(b[x] !== a[x]) //b's count of class x is not the same as a's
			return false;
	}
	
	return true;//Identical
}