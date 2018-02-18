//Permutation via recursion with some info logging to keep track of Stack and progress.

function permute(a){
	let uuid = guid();
	console.log(`${uuid} Passed args: ${a}`);

	//The permutation of a single character is itself
	if(a.length == 1){
		return [a];
    }
	let out = [];
	//Multiple characters. Let's break them into 2 groups (a single element and all the remainder).
	//Each character will get to be that super special single element by the end of the loop.
	for(let i = 0; i < a.length; i++){
		let singleLetter = a.charAt(i);
		let theRest = a.slice(0,i).concat(a.slice(i+1));

		console.log(`${uuid} singleLetter: ${singleLetter} Rest: ${theRest}`);

		let temp = prependLetterToPermutations(singleLetter, theRest);
		
		// Need to "flatten" our arrays. Otherwise we end up with nested arrays(['a',['bc']], not ['abc','bac', ...])
		for(let t of temp){
			out.push(t);
		}
		console.log(`${uuid} Out: ${out}`);

    }
	
	return out;
}

//create pairing of our selected letter with EVERY permutation of the remainder 
// Examples: 
// L:['a'] R['b'] => ['ab']
// L:['a'] R['bc','cb'] => ['abc','acb']
function prependLetterToPermutations(singleLetter, remainderGroup){
	let rPermutations = permute(remainderGroup);

	return rPermutations.map((i)=>singleLetter + i);
}


function guid() {
  function s4() {
    return Math.floor((1 + Math.random()) * 0x10000)
      .toString(16)
      .substring(1);
  }
  return s4() + s4() + '-' + s4() + '-' + s4() + '-' +
    s4() + '-' + s4() + s4() + s4();
}

permute('abcd');