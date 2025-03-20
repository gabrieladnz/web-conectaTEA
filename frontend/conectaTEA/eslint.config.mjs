import globals from 'globals';
import pluginJs from '@eslint/js';
import tseslint from 'typescript-eslint';
import prettier from 'eslint-plugin-prettier';

/** @type {import('eslint').Linter.Config[]} */
export default [
    { files: ['**/*.ts'] },
    { files: ['**/*.ts'], languageOptions: { sourceType: 'commonjs' } },
    { languageOptions: { globals: globals.browser } },
    pluginJs.configs.recommended,
    ...tseslint.configs.recommended,
    {
        rules: {
            quotes: ['error', 'single'],
            'no-console': 'warn',
            indent: ['error', 4],
            'no-unused-vars': ['warn'],
            semi: ['error', 'always'],
            'prettier/prettier': [
                'error',
                {
                    singleQuote: true,
                    trailingComma: 'all',
                    tabWidth: 4,
                },
            ],
        },
    },
    {
        plugins: {
            prettier: prettier,
        },
    },
];
