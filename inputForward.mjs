/**
 * This script exists as a workaround to 
 * limitations of the java input system.
 * For some reason the default java input stream
 * was only writing after receiving a newline character
 * This script stands in between stdin and 
 * the java program, forwarding every input and output.
 * This allows the game to update immediately whenever a
 * key is pressed
 */


import {spawn} from 'child_process'; 

// This is the important feature that java doesn't have
// Raw mode means that js receives messages 
// every time a character is pressed
process.stdin.setRawMode(true);
process.stdin.resume();

// Spawn the java script as a subprocess
const s = spawn('java', ['-cp', '.:./build', 'Main']);

// Whenever data is received from stdin send it over
process.stdin.on('data', (c) => {
    // This is useful as a debug exit that still
    // cleans up the active buffer
	if (c == 'Q') {
		process.exit(0);
	}

    // Write the input 
    s.stdin.write(c);
});
// Forward all outputs from Java to the stdout
s.stdout.pipe(process.stdout);
s.on('exit', () => {
    process.exit();
});

// When the process exits write ANSI escape sequences
// to exit the alternate buffer
process.on('exit', () => {
    process.stdout.write("\u001B[?25h");
    process.stdout.write("\u001B[?47l");
    process.stdout.write("\u001B8");
});
