/**
 * @license
 * Copyright 2018 Google LLC. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * =============================================================================
 */

import * as tf from '@tensorflow/tfjs';

import { TESTE_ENTRADAS, TESTE_SAIDAS } from './test';

import { TREINO_ENTRADAS } from './entradas';
import { TREINO_SAIDAS } from './saidas';

export const ENTRADA_H = 28;
export const ENTRADA_W = 28;

const NUM_CLASSES = 10;

export class MnistData {
  constructor() { }
  async load() {
    this.trainEntradas = TREINO_ENTRADAS;
    this.testEntradas = TESTE_ENTRADAS;
    this.trainSaidas = TREINO_SAIDAS;
    this.testSaidas = TESTE_SAIDAS;
  }

  getTrainData() {
    const xs = tf.tensor4d(
      this.trainEntradas,
      [this.trainEntradas.length / 40, 10, 4, 1]);
    const labels = tf.tensor2d(
      this.trainSaidas, [this.trainSaidas.length / NUM_CLASSES, NUM_CLASSES]);
    return { xs, labels };
  }

  getTestData(numExamples) {
    let xs = tf.tensor4d(
      this.testEntradas,
      [this.testEntradas.length / 40, 10, 4, 1]);
    let labels = tf.tensor2d(
      this.testSaidas, [this.testSaidas.length / NUM_CLASSES, NUM_CLASSES]);

    if (numExamples != null) {
      xs = xs.slice([0, 0, 0, 0], [numExamples, 10, 4, 1]);
      labels = labels.slice([0, 0], [numExamples, NUM_CLASSES]);
    }
    return { xs, labels };
  }
}
