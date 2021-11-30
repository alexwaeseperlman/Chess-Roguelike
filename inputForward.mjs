import {spawn} from 'child_process'; 

process.stdin.setRawMode(true);
process.stdin.resume();

const s = spawn('java', ['-cp', '.:./build', 'Main']);
process.stdin.on('data', (c) => {
	if (c == 'Q') {
		// Toggle buffer back to the standard
		process.exit(0);
	}
    s.stdin.write(c);
});
s.stdout.pipe(process.stdout);
s.on('exit', () => {
    process.exit();
});

process.on('exit', () => {
    process.stdout.write("\u001B[?25h");
    process.stdout.write("\u001B[?47l");
    process.stdout.write("\u001B8");
});
