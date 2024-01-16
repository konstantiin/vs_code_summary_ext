// The module 'vscode' contains the VS Code extensibility API
// Import the module and reference it with the alias vscode in your code below
import * as vscode from 'vscode';
import * as http from 'node:http';

// This method is called when your extension is activated
// Your extension is activated the very first time the command is executed
export function activate(context: vscode.ExtensionContext) {

	// Use the console to output diagnostic information (console.log) and errors (console.error)
	// This line of code will only be executed once when your extension is activated
	console.log('Congratulations, your extension "ai-summary" is now active!');

	function getTextToSummarize(){
    const editor = vscode.window.activeTextEditor;
    let text = null;
		if (editor) {
			const document = editor.document;
			text = document.getText();
    }
    return text;
  }
	let disposable = vscode.commands.registerCommand('ai-summary.summarize', (arg) => {
    console.log(arg);
    let postData = getTextToSummarize();
    if (postData === null){
      console.log("No fileis opened, or some cringe occured");
      return;
    }
    
    let req = http.request(
      {
        hostname: 'localhost',
        port: 8080,
        path: '/awesome_war/control',
        method: 'POST',
        headers: {
             'Content-Type': 'application/json',
             'Content-Length': postData.length
           }
      },
      (res) =>{
          console.log('statusCode:', res.statusCode);
          console.log('headers:', res.headers);
          var ans = "";
          res.on('data', (chunk) => {
            console.log(`BODY: ${chunk}`);
            ans += chunk;
          });
          res.on('end', () => {
            vscode.window.showInformationMessage(`${ans}`);
            console.log('No more data in response.');
          });
        }
    );
    req.on('error', (e) => {
      console.error(e);
    });
    req.write(postData);
    req.end();
    
	});

	context.subscriptions.push(disposable);
}

// This method is called when your extension is deactivated
export function deactivate() {}
